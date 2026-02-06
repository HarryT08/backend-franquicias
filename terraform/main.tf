terraform {
    required_providers {
        mongodbatlas = {
            source  = "mongodb/mongodbatlas"
            version = "1.15"
        }
    }
}

provider "mongodbatlas" {
  public_key  = var.atlas_public_key
  private_key = var.atlas_private_key
}

resource "mongodbatlas_project" "this" {
  name   = var.project_name
  org_id = var.atlas_org_id
}

resource "mongodbatlas_cluster" "this" {
  project_id = mongodbatlas_project.this.id
  name       = "cluster-franquicias"

  provider_name               = "TENANT"
  backing_provider_name       = "AWS"
  provider_region_name        = "US_EAST_1"
  provider_instance_size_name = "M0"
}

resource "mongodbatlas_database_user" "this" {
  username           = var.db_username
  password           = var.db_password
  project_id         = mongodbatlas_project.this.id
  auth_database_name = "admin"

  roles {
    role_name     = "readWrite"
    database_name = "franquicias_db"
  }
}

resource "mongodbatlas_project_ip_access_list" "this" {
  project_id = mongodbatlas_project.this.id
  cidr_block = "0.0.0.0/0"
  comment    = "Acceso abierto para prueba técnica"
}