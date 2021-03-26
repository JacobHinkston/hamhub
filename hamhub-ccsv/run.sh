TEST_FILE_PREFIX=$1
EXPORT_FORMAT=$2

if [ $# -ne 2 ]; then
    echo "
    Usage:
        0: Test CSV prefix.
        1: Format to export to <ft3d, uv5r>
    ";
else
    mvn clean install;
    java -jar ./target/ccsv-1.0.0-SNAPSHOT.jar ./resources/test-csvs/${TEST_FILE_PREFIX}.csv $EXPORT_FORMAT;
fi

