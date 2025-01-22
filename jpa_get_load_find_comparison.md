
# **Difference Between `get()`, `load()`, and `find()` in JPA**

This document explains the differences between `get()`, `load()`, and `find()` methods in JPA and Hibernate, with examples and use cases.

---

## **Overview**

| Method  | Fetch Strategy | API                | Behavior for Non-Existent Entity | Description                            |
|---------|----------------|--------------------|-----------------------------------|----------------------------------------|
| `get()` | Eager          | Hibernate-specific | Returns `null`                   | Retrieves an entity immediately.       |
| `load()`| Lazy           | Hibernate-specific | Throws `ObjectNotFoundException` | Returns a proxy; database access is deferred. |
| `find()`| Eager          | JPA-compliant      | Returns `null`                   | Retrieves an entity immediately.       |

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
  - Fetches the entity lazily (returns a proxy).
  - Throws `ObjectNotFoundException` if the proxy is accessed and the entity does not exist.
  - Useful when the entity might not be needed immediately or when you are sure it exists.

**Example**:
```java
Employee employee = session.load(Employee.class, 1);
System.out.println(employee.getName()); // Triggers a database query if not in cache
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

## **Key Differences**

| Feature            | `get()`                    | `load()`                   | `find()`                     |
|---------------------|----------------------------|----------------------------|------------------------------|
| **API**            | Hibernate-specific         | Hibernate-specific         | JPA-compliant               |
| **Fetch Strategy** | Eager                      | Lazy (returns a proxy)     | Eager                       |
| **Cache Check**    | Yes                        | Yes                        | Yes                         |
| **Return Type**    | Actual entity or `null`    | Proxy or exception         | Actual entity or `null`     |
| **Behavior for Non-Existent Entity** | Returns `null`         | Throws `ObjectNotFoundException` | Returns `null`            |

---

## **Recommendations**

- Use **`find()`** for standard JPA applications.
- Use **`get()`** if you're working directly with Hibernate and need eager fetching.
- Use **`load()`** if you're certain the entity exists and want to defer database access.
