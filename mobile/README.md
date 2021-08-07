# Cara menjalankan generate schema graphql

jalankan command di bawah ini setiap membuat sebuah query baru, mutation baru ataupun schema baru pada hasura anda 

`gradlew downloadApolloSchema --endpoint="https://berbagi-makanan.herokuapp.com/v1/graphql" --schema="app/src/main/graphql/schema.json" --header="x-hasura-admin-secret: your_secret"`