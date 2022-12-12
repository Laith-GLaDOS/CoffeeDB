echo "Building source..." &&
javac -cp ../lib/*.jar ../src/coffeedb/*.java -d ./ &&
echo "Unpacking dependencies..." &&
unzip ../lib/ZiphJSON*.jar > /dev/null &&
rm -r META-INF &&
echo "Creating jar file..." &&
jar cmf ./manifest.mf ./output/CoffeeDB.jar ./coffeedb ./ziph
echo "Cleaning up..."
rm -rf ./coffeedb
rm -rf ziph