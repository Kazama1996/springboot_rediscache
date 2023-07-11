CREATE OR REPLACE PROCEDURE buy_product_by_productId(p_product_id UUID)
LANGUAGE plpgsql
AS 
$$
BEGIN   
    -- Check if the product exists and stock is greater than 0
    IF check_product_stock_by_id(p_product_id) THEN
            
        -- Decrement the stock by 1
        UPDATE product
        SET stock = stock - 1 
        WHERE product_id = p_product_id;
		COMMIT;
    ELSE
        -- Raise an exception if the stock is 0 or the product does not exist
        RAISE EXCEPTION 'This product is sold out or does not exist.';
		ROLLBACK;
    END IF;
    

END;
$$;
