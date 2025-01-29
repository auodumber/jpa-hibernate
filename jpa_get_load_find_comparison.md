
# **Difference Between `get()`, `load()`, and `find()` in JPA**

This document explains the differences between `get()`, `load()`, and `find()` methods in JPA and Hibernate, with examples and use cases.

---

## **Overview**

| Method  | Fetch Strategy | API                | Behavior for Non-Existent Entity | Description                            |
|---------|----------------|--------------------|-----------------------------------|----------------------------------------|
| `get()` | Eager          | Hibernate-specific | Returns `null`                   | Retrieves an entity immediately.       |
| `load()`| Lazy           | Hibernate-specific | Throws `ObjectNotFoundException` | Returns a proxy; database access is deferred. |
| `find()`| Eager          | JPA-compliant      | Returns `null`                   | Retrieves an entity immediately.       |
| `getReference()'| Lazy   | JPA-compliant      | Throws `EntityNotFoundException` | Returns a proxy; database access is deferred |
---

## **1. `get()` (Hibernate-Specific)**

- **Usage**: `Session.get(Class<T> entityType, Serializable id)`
- **Description**:
  - Fetches the entity immediately (eager fetch).
  - Returns `null` if the entity is not found.
  - Checks the persistence context (L1 cache) before querying the database.

**Example**:
```java
Employee employee = session.get(Employee.class, 1);
if (employee == null) {
    System.out.println("Employee not found");
}
```

---

## **2. `load()` (Hibernate-Specific)**

- **Usage**: `Session.load(Class<T> entityType, Serializable id)`
- **Description**:
  - The load method initially returns a proxy object, not the actual entity. This proxy object contains the entity's identifier but does not load the rest of the entity's        data immediately (lazy loading).
  - When any property of the entity (other than the identifier) is accessed for the first time, Hibernate initializes the proxy by executing a query to fetch the actual 
    data from the database.
  - Throws `ObjectNotFoundException` if the proxy is accessed and the entity does not exist.
  - Useful when the entity might not be needed immediately or when you are sure it exists.

**Example**:
```java
Session session = sessionFactory.openSession();

// Use load to get a proxy object
Employee employeeProxy = session.load(Employee.class, 1L);

// Proxy object is returned immediately, no database query yet
System.out.println("Proxy Class: " + employeeProxy.getClass());

// Accessing the ID does not trigger a query
System.out.println("Employee ID: " + employeeProxy.getId());

// Accessing any other property triggers a query
System.out.println("Employee Name: " + employeeProxy.getName());


//If the entity does not exist in the database, an exception is thrown when the proxy is accessed for the first time.
//Exception: A org.hibernate.ObjectNotFoundException is thrown when Hibernate tries to initialize the proxy but fails to find the entity in the database.
```

---

## **3. `find()` (JPA-Compliant)**

- **Usage**: `EntityManager.find(Class<T> entityType, Object primaryKey)`
- **Description**:
  - Fetches the entity immediately (eager fetch).
  - Returns `null` if the entity is not found.
  - Checks the persistence context before querying the database.

**Example**:
```java
Employee employee = entityManager.find(Employee.class, 1);
if (employee == null) {
    System.out.println("Employee not found");
}
```
---
## **4. `getReference()` (JPA-Specific)**

- **Usage**: `EntityManager.getReference(Class<T> entityType, Object primaryKey);`
- **Description**:
      - The method returns a proxy object that acts as a reference to the entity.
      - The actual data for the entity is only loaded from the database when a property of the entity is accessed (lazy loading).
      - If the entity does not exist in the database and you attempt to access its non-primary-key properties, the EntityNotFoundException is thrown:

**Example**
```java
 EntityManager em = entityManagerFactory.createEntityManager();

  // Use getReference to get a proxy object
  Employee employeeProxy = em.getReference(Employee.class, 1L);

  // The proxy is returned immediately, no database query yet
  System.out.println("Proxy Class: " + employeeProxy.getClass());

  // Accessing the ID does not trigger a query
  System.out.println("Employee ID: " + employeeProxy.getId()); 

  // Accessing any other property triggers a query
  System.out.println("Employee Name: " + employeeProxy.getName());

  Employee employeeProxy = em.getReference(Employee.class, 999L); // Entity with ID 999 does not exist
  System.out.println(employeeProxy.getName()); // Throws EntityNotFoundException
```
 
---

## **Key Differences**

| Feature                        | `get()`                     | `load()`                      | `find()`                     | `getReference()`               |
|--------------------------------|-----------------------------|-------------------------------|------------------------------|--------------------------------|
| **API**                        | Hibernate-specific          | Hibernate-specific            | JPA-compliant                | JPA-compliant                  |
| **Fetch Strategy**             | Eager                       | Lazy (returns a proxy)        | Eager                        | Lazy (returns a proxy)         |
| **Cache Check**                | Yes                         | Yes                           | Yes                          | Yes                            | 
| **Return Type**                | Actual entity or `null`     | Proxy or exception            | Actual entity or `null`      | Proxy or exception             |
| **Behavior for Non-Existent Entity** | Returns `null`              | Throws `ObjectNotFoundException` | Returns `null`               | Throws `EntityNotFoundException` |


---
## **Recommendations**

- Use **`find()`** for standard JPA applications.
- Use **`get()`** if you're working directly with Hibernate and need eager fetching.
- Use **`load()`** if you're certain the entity exists and want to defer database access, This API is Deprecated in Hibernate 6.0
- Use **`getReference()`** when you only need the reference (proxy) to the entity, such as setting a foreign key or establishing a relationship between entities, but you      don't need to access the full entity's data immediately
