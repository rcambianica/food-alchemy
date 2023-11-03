mkdir /tmp/spoonacular
java -jar /tmp/swagger-codegen-cli.jar generate \
   -i https://spoonacular.com/application/frontend/downloads/spoonacular-openapi-3.json \
   -l java \
   --library retrofit2 \
   --api-package "com.spoonacular.client" \
   --model-package "com.spoonacular.models" \
   --invoker-package "com.spoonacular" \
   -o /tmp/spoonacular
