# Neotropic SAS 2017
# Kuwaiba Open Network Inventory - Web Service Stub Generator v0.4
# This script generates the Java client side stubs necessary to consume the platform's web service. Use -h flag to see the full list of options

# Defaults
url="http://localhost:8080/kuwaiba/KuwaibaService?wsdl"
destination="src/main/resources"

while getopts u:cd:h option
do
    case "${option}"
    in
        u) url=${OPTARG};;
        c) clean=true;;
        d) destination=${OPTARG};;
        h) echo $'Kuwaiba Open Network Inventory - Web Service Stub Generator v0.4\n -u WSDL URL. Default: http://localhost:8080/kuwaiba/KuwaibaService?wsdl\n -c Clean the destination directory before parsing the WSDL\n -d Destination directory. Default: src';exit;;
    esac
done

if ! type wsimport >/dev/null; then
    echo "wsimport command not found. This command is in the bin folder of your Java installation. Check if your PATH is correctly pointing there."
    exit 1;
fi

if [ -z ${clean+x} ]
then
    echo "Cleaning ${destination}/org/inventory/communications/wsclient..."
    rm -rf "${destination}/org/inventory/communications/wsclient"
fi

wsimport -d $destination -p org.inventory.communications.wsclient -Xnocompile $url -b kuwaibaAsync.xml 

if [ $? -eq 0 ]
then
    #Here we patch the TransientQuery class with the missing setters that wsimport does not generate
    echo "Patching TransientQuery class..."
    setters="public void setAttributeNames(List<String> attributeNames) {
        this.attributeNames = attributeNames;
    }

    public void setAttributeValues(List<String> attributeValues) {
        this.attributeValues = attributeValues;
    }

    public void setConditions(List<Integer> conditions) {
        this.conditions = conditions;
    }

    public void setJoins(List<TransientQuery> joins) {
        this.joins = joins;
    }

    public void setVisibleAttributeNames(List<String> visibleAttributeNames) {
        this.visibleAttributeNames = visibleAttributeNames;
    }"

    head -n -2  "${destination}/org/inventory/communications/wsclient/TransientQuery.java" > temp.txt

    echo "${setters}" >> temp.txt
    echo $'\n}' >> temp.txt
    mv temp.txt "${destination}/org/inventory/communications/wsclient/TransientQuery.java"
    
    #Here we patch the ClassInfoLight class with the missing hashCode, equals and toString that wsimport does not generate
    echo "Patching ClassInfoLight class..."
    setters="public int hashCode() {
        int hash = 7;
        hash = 97 * hash + (int) (this.id ^ (this.id >>> 32));
        return hash;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ClassInfoLight other = (ClassInfoLight) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
        
    public String toString() {
        return displayName != null && !displayName.isEmpty() ? displayName : className;
    }"

    head -n -2  "${destination}/org/inventory/communications/wsclient/ClassInfoLight.java" > temp.txt

    echo "${setters}" >> temp.txt
    echo $'\n}' >> temp.txt
    mv temp.txt "${destination}/org/inventory/communications/wsclient/ClassInfoLight.java"
    
    #Here we patch the RemoteObjectLight class with the missing hashCode, equals and toString that wsimport does not generate
    echo "Patching RemoteObjectLight class..."
    setters="public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.oid ^ (this.oid >>> 32));
        return hash;
    }
    
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final RemoteObjectLight other = (RemoteObjectLight) obj;
        if (this.oid != other.oid) {
            return false;
        }
        return true;
    }
    
    public String toString() {
        return name + \" [\" + className + \"]\";
    }"

    head -n -2  "${destination}/org/inventory/communications/wsclient/RemoteObjectLight.java" > temp.txt

    echo "${setters}" >> temp.txt
    echo $'\n}' >> temp.txt
    mv temp.txt "${destination}/org/inventory/communications/wsclient/RemoteObjectLight.java"
        
    echo "Patching done."    
else
    echo wsimport failed. Aborting any further actions.
fi

