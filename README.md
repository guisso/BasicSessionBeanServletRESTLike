# BasicSessionBeanServletRESTLike
A basic usage of Jakarta Enterprise Bean via Jakarta Servlet as REST-like.

Review the commits. They show the logical sequence to building the complete application. Basically:

- Configure the data source and parameters on persistence.xml
- Build the entity and its EJB (stateless session bean) with CRUD operations
- Create the servlet to use EJB services
- Perform tests all the time, always observing the results reflected in the database

## Postman Tests
Pay attention to variable {{baseURL}}, params, headers, and body.

[Basic tests](https://www.postman.com/lguisso-6942859/workspace/personal-workspace/collection/47767724-d3073648-16b8-4170-a3dd-f03ed7c3c16d?action=share&creator=47767724)