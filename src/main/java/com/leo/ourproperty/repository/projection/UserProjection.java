package com.leo.ourproperty.repository.projection;

import jakarta.persistence.Column;

public interface UserProjection {
     String getName();

     String getEmail();

     String getCpf();

     String getPassword();
}
