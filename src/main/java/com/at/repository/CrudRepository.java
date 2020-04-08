package com.at.repository;

import java.util.List;

public interface CrudRepository<T,K> {
    List<T> findAll();     //Read (SELECT * )  //returneaza o colectie de obiecte => folosim List
    void save(T t);           // Create and Update (INSERT and UPDATE)
    void deleteById(K id); // DELETE FROM WHERE id = "id"
    T findById(K id);    //SELECT * FROM TABLE WHERE id = "id"

}
