package com.apps.customerbackend.Repository;

import com.apps.customerbackend.Facade.GenericCRUDRepository;
import com.apps.customerbackend.Models.table.Customer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CustomerRepository extends GenericCRUDRepository<Customer, Long> {

    public static final String getCustomerServerSide = "declare @p_keywords varchar(max) = :p_search, " +
            "    @p_page_number int = :p_page_number, " +
            "    @p_rowspage int = :p_rows_page, " +
            "    @p_order_by int = :p_order_by, " +
            "    @p_sort_by varchar(5) = :p_sort_by " +
            "select id, " +
            "       name            as nama, " +
            "       address         as alamat, " +
            "       city            as kota, " +
            "       province        as provinsi, " +
            "       register_date   as tgl_registrasi, " +
            "       status, " +
            "       count(1) over() as [rowcount] " +
            "from customers " +
            "where ( " +
            "                  name like '%' + @p_keywords + '%' or " +
            "                  address like '%' + @p_keywords + '%' or " +
            "                  city like '%' + @p_keywords + '%' or " +
            "                  province like '%' + @p_keywords + '%' or " +
            "                  register_date like '%' + @p_keywords + '%' or " +
            "                  status like '%' + @p_keywords + '%' " +
            "          ) " +
            "order by case " +
            "             when @p_sort_by = 'asc' then case @p_order_by " +
            "                      when 1 then cast(left(name, patindex('%[^0-9]%', name + 'a') - 1) as int) " +
            "                      when 2 then cast(left(address, patindex('%[^0-9]%', name + 'a') - 1) as int) " +
            "                      when 3 then cast(left(name, patindex('%[^0-9]%', name + 'a') - 1) as int) " +
            "                      when 4 then cast(left(city, patindex('%[^0-9]%', name + 'a') - 1) as int) " +
            "                      when 5 then cast(register_date as sql_variant) " +
            "                      when 6 then cast(left(status, patindex('%[^0-9]%', name + 'a') - 1) as int) end end, " +
            "         case " +
            "             when @p_sort_by = 'desc' then case @p_order_by " +
            "                       when 1 then cast(left(name, patindex('%[^0-9]%', name + 'a') - 1) as int) " +
            "                       when 2 then cast(left(address, patindex('%[^0-9]%', name + 'a') - 1) as int) " +
            "                       when 3 then cast(left(name, patindex('%[^0-9]%', name + 'a') - 1) as int) " +
            "                       when 4 then cast(left(city, patindex('%[^0-9]%', name + 'a') - 1) as int) " +
            "                       when 5 then cast(register_date as sql_variant) " +
            "                       when 6 then cast(left(status, patindex('%[^0-9]%', name + 'a') - 1) as int) end end desc " +
            "offset @p_page_number rows fetch next @p_rowspage rows only";

    @Query(nativeQuery = true, value = getCustomerServerSide)
    List<Map<String, Object>> get(String p_search, int p_page_number, int p_rows_page, int p_order_by, String p_sort_by);
}
