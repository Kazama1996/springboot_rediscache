CREATE OR REPLACE FUNCTION check_product_stock_by_id(p_product_id UUID)
RETURNS BOOLEAN
LANGUAGE SQL
AS $$
    SELECT EXISTS (
        SELECT 1
        FROM product 
        WHERE product_id = p_product_id
        AND stock > 0 FOR UPDATE
    );
$$;