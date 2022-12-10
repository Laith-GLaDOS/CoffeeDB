echo "Building source..." &&
javac -cp ../lib/*.jar ../src/*.java -d ./ &&
echo "Unpacking dependencies..." &&
unzip ../lib/*.jar > /dev/null &&
echo "Creating jar file with dependencies injected..." &&
jar cmf ./manifest.mf ./output/CoffeeDB.jar ./*.class ./ziph &&
echo "Cleaning up..." &&
rm ./*.class &&
rm -r ziph META-INF