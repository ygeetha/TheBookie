#!/bin/sh

#     Copyright 2011 - Sardegna Ricerche, Distretto ICT, Pula, Italy
#   
#    Licensed under the EUPL, Version 1.1.
#    You may not use this work except in compliance with the Licence.
#    You may obtain a copy of the Licence at:
#   
#     http://www.osor.eu/eupl
#   
#    Unless required by applicable law or agreed to in  writing, software distributed under the Licence is distributed on an "AS IS" basis,
#    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
#    See the Licence for the specific language governing permissions and limitations under the Licence.
#    In case of controversy the competent court is the Court of Cagliari (Italy).


#FILE=/tmp/mydb
TMPDIR="/tmp/"
BASEDIR=$(dirname $0)
LOCANDAVERSION="3.0"
LOCANDADIST="${TMPDIR}locanda-${LOCANDAVERSION}_dist"


MAX=100
IDLE=30
WAIT=10000
USERNAME="LOCANDA"
PASSWORD="LOCANDA"
DRIVERCLASS="org.hsqldb.jdbcDriver"
URL="jdbc:hsqldb:hsql://localhost/locanda"

HSQLURL="jdbc:hsqldb:hsql://localhost/locanda"
HSQLDRIVERCLASS="org.hsqldb.jdbcDriver"

MYSQLURL="jdbc:mysql://localhost:3306/locanda"
MYSQLDRIVERCLASS="com.mysql.jdbc.Driver"

SOLRP=$HOME



#$1 user (root mysql)
#$2 passwd 
#$3 host
#$4 port
#$5 NEWMYSQLUSER
#$6 NEWMYSQLPASSWD
mysqlLocandaCreate(){
    echo "Creating Database Locanda on mysql://$3:$4"
    mysqladmin --user=$1 --password=$2 --host=$3 --port=$4 create locanda
    
    if [ $? = 0 ]; then
	echo "\nCreating schemas..."
	mysql --user=$1 --password=$2 --host=$3 --port=$4 locanda <  ${BASEDIR}/data/locanda.sql
	
    else
	echo "\nDatabase locanda alredy exist"
	echo "would you drop and restore a new one? y/[n]"
	read IST
	IST="${IST:="n"}"
	
	if [ $IST = 'y' ]; then
	    echo "Dropping and restoring..."
	    echo " Dropping Database."
	    mysqladmin --user=$1 --password=$2 --host=$3 --port=$4 --force drop locanda
	    echo " Creating Database."
	    mysqladmin --user=$1 --password=$2 --host=$3 --port=$4 create locanda
	    echo " Creating Schemas."
	    mysql --user=$1 --password=$2 --host=$3 --port=$4 locanda <  ${BASEDIR}/data/locanda.sql
	    
	fi
    fi

    echo "Now we create a new MySqlUser for locanda database."
    echo "Creating "
    echo "\t user:\t $5"
    echo "\t password:\t $6"
    echo "User can SELECT, INSERT, DELETE, UPDATE"
    cat ${BASEDIR}/data/newuser.sql | sed -e "s/newuser/$5/" | sed -e "s/password/$6/" > ${TMPDIR}/newuser.sql
    mysql --user=$1 --password=$2 --host=$3 --port=$4 <  ${TMPDIR}/newuser.sql
    
}


solrConfig(){
    IST=n
    while [ ! $IST = y ]; do {
	    echo "\nWhere do you want to put the solr folder?"
	    
	    echo "Default [$HOME]"
	    read SOLRP
	    SOLRP="${SOLRP:=$HOME}"

	    
	    echo "This is your solr path. Correct? y/[n]"
	    echo "${SOLRP}"
	    read IST
	    IST="${IST:="n"}"

	}
    done


    ESOLRP=`echo ${SOLRP} | sed -e 's/\/$//'|  sed -f ${BASEDIR}/data/f2b.sed  ` 
    
    echo "\nCreating configuration files"
    cat  ${BASEDIR}/data/solr.properties |
    sed -e "s/SOLRPATH/${ESOLRP}/" >  ${LOCANDADIST}/WEB-INF/solr.properties 

    echo "\nExtracting data in ${SOLRP}"
    tar -zxvf ${BASEDIR}/data/solr.tar.gz -C ${SOLRP} > /dev/null 2>&1
}


clean(){
    rm -rf ${LOCANDADIST}  > /dev/null 2>&1
}

extract(){
    echo "Extracting..."
    unzip ${BASEDIR}/locanda.war -d ${LOCANDADIST} > /dev/null 2>&1
}


warbuild(){
    echo "Creating War..."
    cd ${LOCANDADIST}
    zip -r ${TMPDIR}/locanda.war ./* > /dev/null 2>&1
}

edit(){
    echo "Editing configuration..."
    
    EURL=`echo ${URL} | sed -f ${BASEDIR}/data/f2b.sed`
    EUSERNAME=`echo ${USERNAME} | sed -f ${BASEDIR}/data/f2b.sed`
    EPASSWORD=`echo ${PASSWORD} | sed -f ${BASEDIR}/data/f2b.sed`
    EDRIVERCLASS=`echo ${DRIVERCLASS} | sed -f ${BASEDIR}/data/f2b.sed`
    
    cat  ${BASEDIR}/data/context.xml |
    sed -e "s/maxActive\=\"100\"/maxActive\=\"${MAX}\"/" |
    sed -e "s/maxIdle\=\"30\"/maxIdle\=\"${IDLE}\"/"     |
    sed -e "s/maxWait\=\"10000\"/maxWait\=\"${WAIT}\"/"  |
    sed -e "s/username\=\"SA\"/username\=\"${EUSERNAME}\"/"  |
    sed -e "s/password\=\"\"/password\=\"${EPASSWORD}\"/"  |
    sed -e "s/driverClassName\=\"org\.hsqldb\.jdbcDriver\"/driverClassName\=\"${EDRIVERCLASS}\"/"  |
    sed -e "s/url\=\"jdbc\:hsqldb\:hsql\:\/\/localhost\/locanda\"/url\=\"${EURL}\"/" > ${LOCANDADIST}/META-INF/context.xml
} 

install(){
    IST=n
    while [ ! $IST = y ]; do {
	    echo "Where do you want to install locanda.war?"
	    echo "must be in you Tomcat app directory: [${CATALINA_HOME}/webapps/]"
	    read INSTALLDIR
	    INSTALLDIR="${INSTALLDIR:=${CATALINA_HOME}/webapps/}"

	    echo "\n Would you like to install locanda in ${INSTALLDIR} ??"
	    echo "Correct? y/[n]"
	    read IST
	    IST="${IST:="n"}"
	}
    done
    
    echo "Installing locanda ..."
    mv -i ${TMPDIR}/locanda.war ${INSTALLDIR};

}


interactiveEdit(){
    IST=n
    while [ ! $IST = y ]; do {
	    echo "\nSetting Parameters"
	    
	    echo "Username: [LOCANDA]"
	    read USERNAME
	    USERNAME="${USERNAME:=LOCANDA}"

	    echo "Password: [LOCANDA]"
	    read PASSWORD
	    PASSWORD="${PASSWORD:=LOCANDA}"

	    echo "Max connections: Default [100]"
	    read MAX
	    MAX="${MAX:=100}"

	    echo "Idle connections: Default [30]"
	    read IDLE
	    IDLE="${IDLE:=30}"

	    echo "Wait connections: Default [10000]"
	    read WAIT
	    WAIT="${WAIT:=10000}"

	    echo "Set Database Driverclass: Default [$1]"
	    read DRIVERCLASS
	    DRIVERCLASS="${DRIVERCLASS:=$1}"
	    
	    echo "Set Url to Database: Default [$2]"
	    read URL
	    URL="${URL:=$2}"

	    echo "RESUME:"
	    echo "Max connections:\t${MAX}"
	    echo "Max idle:\t\t${IDLE}"
	    echo "Max wait:\t\t${WAIT}"
	    echo "Username:\t\t${USERNAME}"
	    echo "Password:\t\t${PASSWORD}"
	    echo "Driverclass:\t\t${DRIVERCLASS}"
	    echo "Url:\t\t\t${URL}"

	    echo ""
	    echo "Correct? y/[n]"
	    read IST
	    IST="${IST:="n"}"
	}
    done
}









echo "Welcome to locanda installation:"


DB="0"
while [  ! \( ${DB} = "1" -o ${DB} = "2" \) ]; do {

	echo ""
	echo "wich db do you want to use??"
	echo " [1]: HyperSQL. (you don't need to install other sw)"
	echo " [2]: MySql. (you need to install it!)"
	echo ""
	echo "Please chose 1 or 2: [1]Default "
	
	read DB
	DB="${DB:=1}"
}
done


case ${DB} in
    1)
	echo "HSQL"
	echo "Please install hsql service, you it can found in data/hsql dir"
	interactiveEdit $HSQLDRIVERCLASS $HSQLURL
	;;
    2)
	echo "MySQL"
	interactiveEdit  $MYSQLDRIVERCLASS $MYSQLURL
	echo "\nNow insert your admin credential for MySql"
	echo "we configure a new user and set the locanda database"
	echo "Username: "
	read MADMIN
	echo "Password: "
	read MPASSWD
	echo "Host: [localhost] "
	read MHOST
	MHOST="${MHOST:=localhost}"
	echo "Port: [3306] "
	read MPORT
	MPORT="${MPORT:=3306}"
	mysqlLocandaCreate ${MADMIN} ${MPASSWD} ${MHOST} ${MPORT} ${USERNAME} ${PASSWORD}
	;;
esac




clean
extract
solrConfig
edit
warbuild
install
#clean

echo "Enjoy Locanda!"

exit 0



