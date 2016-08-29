#### API for building and enforcing configurations of the Protégé 5 client-server
___

The design of the API follows a role-based access control approach, wherein the decision of whether a user is allowed to perform some operation is dictated by the user's role assignments. The central builder for configurations is the `ConfigurationBuilder`. This API revolves around four concepts:

A **user** is an actor in the client-server, and can be registered and authenticated using the API's default encryption, or with an external user authenticator (e.g., LDAP). There are two default users: The *root* user, who has the ability to perform all default operations, and the *guest* user, who can do read-only default operations.

An **operation** is a server-instruction, such as *delete user* or *add ontology annotation axiom*. There are three types of operations: **read** (e.g., *open project*), **write** (e.g., *add ontology annotation*) and **execute** (e.g., *stop server*). This division of operation types helps formulating roles for read-, write-, or execute-only operations, and is inspired by UNIX-style file system permissions.

A **role** is a collection of **(allowed) operations**, e.g., the role *Editor* allows *add axiom* and *remove axiom* operations. There are two default roles: the **admin** role, which contains all default operations, and the **guest** role that contains all default read operations. Note that roles are reusable items, as in, once a role has been defined, an administrator can do a batch assignment of that role to multiple users (e.g., as opposed to assigning allowed operations to users on a per-user basis). 

A **project** corresponds to an ontology; it has some details like an identifier, name, description, and the actual ontology file (location). A project can also have some options associated with it, such as the set of annotation properties that, once introduced (e.g., a unique term identifier), cannot be changed. The API contains a special project that is a *global project*, denoted **All Projects**, which allows assigning global roles. That is, when a user is assigned a role `R` in the global project, the user can perform all operations in `R` on every project.


With the above pieces an access-control policy can be built by assigning roles to users within projects:

A **role assignment** is a triple of the form **UserId - RoleId - ProjectId**, which reads: a user has some role within some project, for example: *joe - editor - koala*. Role assignments are done on a per-project basis, allowing an administrator to give users different roles in different projects.
