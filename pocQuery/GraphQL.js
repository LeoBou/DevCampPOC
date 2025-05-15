import { ApolloServer, gql } from 'apollo-server';
import GraphQLJSON from 'graphql-type-json';
import fs from 'fs';

// Fonction de lecture du fichier JSON
const loadData = () => {
  const rawData = fs.readFileSync('./data.json');
  return JSON.parse(rawData);
};

const typeDefs = gql`
  scalar JSON

  type Item {
  id: ID
  nom: String
  proprietaire: String
  latitude: String
  longitude: String
}

type Query {
  getData(filter: JSON): [Item]
}
`;

const resolvers = {
  JSON: GraphQLJSON,
  Query: {
    getData: (_, { filter }) => {
      const data = loadData();
      let result = data;
      if (filter) {
        result = data.filter(item =>
          Object.entries(filter).every(([key, value]) => String(item[key]) === String(value))
        );
      }
      return result;
    },
  },
};


const server = new ApolloServer({ typeDefs, resolvers });

server.listen().then(({ url }) => {
  console.log(`http://localhost:4000/`);
});
