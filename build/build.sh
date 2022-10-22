echo "Building source..." &&
javac ../src/*.java -d ./ &&
echo "Creating jar file..." &&
jar cmf ./manifest.mf ./output/CoffeeKVS.jar ./*.class &&
echo "Cleaning up..." &&
rm ./*.class