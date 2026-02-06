output "mongodb_connection_string" {
  description = "MongoDB Atlas connection string"
  value       = mongodbatlas_cluster.this.connection_strings[0].standard_srv
}