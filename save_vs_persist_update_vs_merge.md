
# Hibernate Methods Comparison: `save()`, `persist()`, `update()`, and `merge()`

## Overview

In Hibernate, the methods `save()`, `persist()`, `update()`, and `merge()` are used for interacting with entities. These methods are used for persisting new entities, updating existing entities, and handling detached entities.

This document provides a comparison between these methods and details about their behavior, as well as important changes introduced in Hibernate 6.0.

## Method Comparison

### 1. `save()`

- **Purpose**: Used to persist a new entity or save an existing entity by associating it with the session.
- **Behavior**: If the entity has no ID (or ID is `null`), it will be treated as a new entity, and an `INSERT` statement will be executed. If the entity has an ID, it will be treated as a new entity and an `INSERT` will be executed anyway, potentially causing issues if the entity already exists (e.g., violating primary key constraints).
- **Detached Entity**: If the entity is detached (not in the current session context), using `save()` will treat it as a new entity, resulting in an `INSERT` query even if it exists in the database.
- **Extras**: Insert Query is not immediately fired but the insert query is created added in insertionList of session's ActionQueue when session flush all the queries in that list will be executed
- **Hibernate 6.0**: The `save()` method is **deprecated** in Hibernate 6.0. It is recommended to use `persist()` for new entities instead.
- **When Insert/Update Query Fires**:
    - `INSERT` query is fired when the entity is transient or has no existing ID.
    -  `UPDATE` query is fired when the entity is already in the context/session. and if we modify its properties
- **Usage**:
  ```java
  session.save(student); // Saves the new student entity
  ```

### 2. `persist()`

- **Purpose**: Used to make a transient entity persistent.
- **Behavior**: Similar to `save()`, it persists new entities by associating them with the session. However, it does not return the entity with its ID (unlike `save()`). It is the recommended method for persisting new entities.
- **Detached Entity**: If a detached entity is passed to `persist()`, it will throw an exception, as `persist()` is meant only for transient entities.

- **Why does persist() throw an exception for detached entities?**:
  The key difference here is that persist() assumes the entity is new (transient) and not yet in the database. If the entity already has an ID (which would indicate it was previously persisted or has been loaded from the database), JPA assumes it is detached and not newly created, so it cannot be persisted again using persist().

- **Hibernate 6.0**: This method is still supported and is the recommended way to persist new entities.
- **When Insert/Update Query Fires**:
    - `INSERT` query is fired when the entity is transient (no ID).
-  `UPDATE` query is fired when the entity is already in the context/session. and if we modify its properties
- **Usage**:
- **Usage**:
  ```java
  session.persist(student); // Persist the new student entity
  ```

### 3. `update()`

- **Purpose**: Used to update an existing entity that is already associated with the session.
- **Behavior**: If the entity is in the **detached state** (i.e., it was previously saved but not currently associated with a session), using `update()` will cause Hibernate to reattach it and update it in the database. This can lead to problems if the entity was modified externally (i.e., outside the current session).
- **Detached Entity**: When a detached entity is passed to `update()`, it will be reattached to the session and an `UPDATE` query will be fired.

**Extra**: If We Trying to call update on a transient instance will result in an exception

- **Hibernate 6.0**: The `update()` method is **deprecated** in Hibernate 6.0. It is recommended to use `merge()` for updating entities.
- **When Insert/Update Query Fires**:
    - `UPDATE` query is fired when the entity exists in the session and has been modified.
    - `INSERT` query is never fired 
    - should only be used for detached entities that already exist in the database.
- **Usage**:
  ```java
  session.update(student); // Updates the student entity
  ```

### 4. `merge()`

- **Purpose**: Used to merge a detached entity (an entity that is no longer associated with the current session).
- **Behavior**: The `merge()` method will reattach the detached entity and synchronize it with the database. If the entity does not exist in the database, it will be inserted. If the entity already exists, it will be updated. This is the recommended method for updating detached entities.
- **Detached Entity**: When a detached entity is passed to `merge()`, it will be reattached and the session will either update or insert it as needed.
- **Hibernate 6.0**: The `merge()` method is not deprecated and is the recommended way to handle detached entities.
- **When Insert/Update Query Fires**:
    - `INSERT` query is fired if the entity is detached and does not exist in the database.
    - `UPDATE` query is fired if the entity already exists in the database and is modified.
- **Usage**:
  ```java
  session.merge(student); // Merges the detached student entity
  ```

## Key Differences

| Method    | Purpose                                      | Entity State | Return Value      | Detached Entity Behavior  | Hibernate 6.0 |
|-----------|----------------------------------------------|--------------|-------------------|---------------------------|---------------|
| `save()`  | Save a new entity or re-save an existing one | Transient    | Returns entity ID | Inserts detached entity as new | Deprecated    |
| `persist()`| Persist a new entity                       | Transient    | No return value   | Throws exception for detached entity | Supported     |
| `update()`| Update an entity                            | Detached     | No return value   | Reattaches and updates the entity | Deprecated    |
| `merge()` | Merge a detached entity or persist a new one | Detached     | Returns merged entity | Reattaches and updates/creates the entity | Supported     |

## Conclusion

- In **Hibernate 6.0**, both `save()` and `update()` methods are **deprecated**. It is recommended to use `persist()` for new entities and `merge()` for updating detached entities.
- Always prefer `merge()` when working with detached entities to avoid potential issues like data inconsistencies or unnecessary insertions.


# Why Does Hibernate Fire `INSERT` and `UPDATE` on Two Consecutive `persist()` Calls?

When calling `persist()` twice on the same object in Hibernate, it results in an `INSERT` followed by an `UPDATE`. This happens due to Hibernate's entity state management in its persistence context.

## Step-by-Step Explanation

1. **First `persist()` Call → `INSERT` Query**
  - The entity is in the **transient state** (not yet managed by Hibernate).
  - When `persist(entity)` is called, Hibernate moves the entity to the **managed state** and schedules an `INSERT` query.
  - The actual `INSERT` query is executed when the transaction is committed or when a flush occurs.

2. **Second `persist()` Call → `UPDATE` Query**
  - Since the entity is already in the **managed state**, calling `persist()` again has no direct effect.
  - However, if any property of the entity is modified before the transaction is committed, Hibernate detects the change and schedules an `UPDATE` query to synchronize the entity state with the database.

## Why Does Hibernate Fire an `UPDATE`?

Hibernate follows the **"Entity Snapshot Mechanism"**, meaning it tracks changes to managed entities. Even if no changes are explicitly made, Hibernate might still detect modifications due to:

- Automatic timestamp updates (`@Version` or `@LastModifiedDate` fields).
- Changes in relationships (`@OneToMany`, `@ManyToOne`).
- Dynamic proxies triggering lazy-loading, causing an unintentional dirty check.

## How to Avoid the `UPDATE`?

- Ensure that `persist()` is called only once per entity.
- Use `merge()` instead if the entity might already be managed.



