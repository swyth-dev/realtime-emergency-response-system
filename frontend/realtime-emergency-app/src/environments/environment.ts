export const environment = {
  production: false,
  hospitalServiceBaseUrl: 'http://localhost:8081/v1', // Development API URL
  emergencyServiceBaseUrl: 'http://localhost:8082/v1' // TODO: refactor when you add API Gateway to ensure only one URL and port is called by client
};
