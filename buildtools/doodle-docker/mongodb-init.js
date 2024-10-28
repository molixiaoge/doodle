db.getSiblingDB("admin").auth(
  process.env.MONGO_INITDB_ROOT_USERNAME,
  process.env.MONGO_INITDB_ROOT_PASSWORD
);

db.getSiblingDB("admin").createUser({
  user: process.env.MONGO_INITDB_ADMIN_USERNAME,
  pwd: process.env.MONGO_INITDB_ADMIN_PASSWORD,
  roles: [{
    role: "readWriteAnyDatabase",
    db: "admin"
  },{
    role: "userAdminAnyDatabase",
    db: "admin"
  }]}
);
