package com.bmdb.business.actor;

import org.springframework.data.repository.CrudRepository;

public interface ActorRepository extends CrudRepository<Actor, Integer> {
//	Actor findByUserNameAndPassword(String uname, String pwd);
}