package com.techbank.cqrs.core.infrastructure;

import com.techbank.cqrs.core.domain.BaseEntity;
import com.techbank.cqrs.core.query.BaseQuery;
import com.techbank.cqrs.core.query.QueryHandlerMethod;

import java.util.List;

public interface QueryDispatcher {

    <T extends BaseQuery> void registerHandler(Class<T> type, QueryHandlerMethod<T> handlerMethod);

    <U extends BaseEntity> List<U> send(BaseQuery query);

}
