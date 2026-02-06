variable "atlas_public_key"{
    type = string
    description = "MongoDB Atlas public API key"
}

variable "atlas_private_key"{
    type = string
    description = "MongoDB Atlas private API key"
}

variable "atlas_org_id"{
    type = string
    description = "MongoDB Atlas organization ID"
}

variable "project_name"{
    type = string
    default = "franquicias-project"
}

variable "db_username"{
    type = string
    default = "adminUser"
}

variable "db_password"{
    type = string
    sensitive = true
}