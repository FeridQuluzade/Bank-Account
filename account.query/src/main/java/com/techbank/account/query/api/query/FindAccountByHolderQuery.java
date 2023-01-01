package com.techbank.account.query.api.query;

import com.techbank.cqrs.core.query.BaseQuery;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FindAccountByHolderQuery extends BaseQuery {

    private String accountHolder;

}
