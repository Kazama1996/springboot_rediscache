-- find post_content and its author by given a userId

create or replace function get_posts_by_userId(p_user_id UUID)
RETURNS TABLE(post_content varchar(255) , author varchar(255))
LANGUAGE plpgsql
AS $$
BEGIN
	RETURN QUERY(
		select posts.content post_content, app_user.profile_name author 
		FROM posts inner join app_user on posts.user_id_fk = app_user.user_id 
		WHERE user_id = p_user_id
	); 
END;
$$;