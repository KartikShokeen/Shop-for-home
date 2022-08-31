CREATE DATABASE ecommerce;
USE ecommerce;
CREATE TABLE IF NOT EXISTS ecommerce.cart
(
    user_id bigint NOT NULL,
    CONSTRAINT cart_pkey1 PRIMARY KEY (user_id)
);


CREATE TABLE IF NOT EXISTS ecommerce.discount
(
    id character varying(255) NOT NULL,
    status bigint,
    CONSTRAINT discount_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS ecommerce.product_category
(
    category_id integer NOT NULL,
    category_name character varying(255) ,
    category_type integer,
    create_time timestamp ,
    update_time timestamp ,
    CONSTRAINT product_category_pkey PRIMARY KEY (category_id),
    CONSTRAINT uk_6kq6iveuim6wd90cxo5bksumw UNIQUE (category_type)
);

ALTER TABLE `ecommerce`.`product_category` 
CHANGE COLUMN `category_id` `category_id` INT NOT NULL AUTO_INCREMENT ;


CREATE TABLE IF NOT EXISTS ecommerce.product_info
(
    product_id character varying(255)  NOT NULL,
    category_type integer DEFAULT 0,
    create_time timestamp ,
    product_description character varying(255) ,
    product_icon character varying(255) ,
    product_name character varying(255)  NOT NULL,
    product_price numeric(19,2) NOT NULL,
    product_status integer DEFAULT 0,
    product_stock integer NOT NULL,
    update_time timestamp ,
    CONSTRAINT product_info_pkey PRIMARY KEY (product_id),
    CONSTRAINT product_info_product_stock_check CHECK (product_stock >= 0)
);

CREATE TABLE IF NOT EXISTS ecommerce.users
(
    id bigint NOT NULL,
    active boolean NOT NULL,
    address character varying(255) ,
    email character varying(255) ,
    name character varying(255) ,
    password character varying(255) ,
    phone character varying(255) ,
    role character varying(255) ,
    CONSTRAINT users_pkey PRIMARY KEY (id),
    CONSTRAINT uk_sx468g52bpetvlad2j9y0lptc UNIQUE (email)
);

ALTER TABLE `ecommerce`.`users` 
CHANGE COLUMN `id` `id` BIGINT NOT NULL AUTO_INCREMENT ;



CREATE TABLE IF NOT EXISTS ecommerce.order_main
(
    order_id bigint NOT NULL,
    buyer_address character varying(255) ,
    buyer_email character varying(255) ,
    buyer_name character varying(255) ,
    buyer_phone character varying(255) ,
    create_time timestamp,
    order_amount numeric(19,2) NOT NULL,
    order_status integer NOT NULL DEFAULT 0,
    update_time timestamp,
    CONSTRAINT order_main_pkey PRIMARY KEY (order_id)
);

ALTER TABLE `ecommerce`.`order_main` 
CHANGE COLUMN `order_id` `order_id` BIGINT NOT NULL AUTO_INCREMENT ;



CREATE TABLE IF NOT EXISTS ecommerce.product_in_order
(
    id bigint NOT NULL AUTO_INCREMENT,
    category_type integer NOT NULL,
    count integer,
    product_description character varying(255)  NOT NULL,
    product_icon character varying(255) ,
    product_id character varying(255) ,
    product_name character varying(255) ,
    product_price numeric(19,2) NOT NULL,
    product_stock integer,
    cart_user_id bigint,
    order_id bigint,
    CONSTRAINT product_in_order_pkey PRIMARY KEY (id),
    CONSTRAINT fkt0sfj3ffasrift1c4lv3ra85e FOREIGN KEY (order_id)
        REFERENCES ecommerce.order_main (order_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT product_cart_fkey FOREIGN KEY (cart_user_id)
        REFERENCES ecommerce.cart (user_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        ,
    CONSTRAINT product_in_order_count_check CHECK (count >= 1),
    CONSTRAINT product_in_order_product_stock_check CHECK (product_stock >= 0)
);



CREATE TABLE IF NOT EXISTS ecommerce.wishlist
(
    id bigint NOT NULL AUTO_INCREMENT,
    created_date timestamp ,
    user_id bigint,
    product_id character varying(255),
    CONSTRAINT wishlist_pkey PRIMARY KEY (id),
    CONSTRAINT product_wish_fkey FOREIGN KEY (product_id)
        REFERENCES ecommerce.product_info (product_id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT user_wish_Fkey FOREIGN KEY (user_id)
        REFERENCES ecommerce.users (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

ALTER TABLE `ecommerce`.`discount`
ADD COLUMN user_email VARCHAR(255);

ALTER TABLE `ecommerce`.`discount` 
ADD INDEX `user_email_fkey_idx` (`user_email` ASC) VISIBLE;
;
ALTER TABLE `ecommerce`.`discount` 
ADD CONSTRAINT `user_email_fkey`
  FOREIGN KEY (`user_email`)
  REFERENCES `ecommerce`.`users` (`email`)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;
  
  ALTER TABLE `ecommerce`.`discount` 
DROP PRIMARY KEY;
;

ALTER TABLE `ecommerce`.`discount` 
ADD COLUMN `coupon` VARCHAR(255) NULL AFTER `user_email`,
CHANGE COLUMN `id` `id` BIGINT NOT NULL ,
ADD PRIMARY KEY (`id`);
;

ALTER TABLE `ecommerce`.`discount` 
CHANGE COLUMN `id` `id` BIGINT NOT NULL AUTO_INCREMENT ;



INSERT INTO ecommerce.product_category VALUES (2147483641, 'Furnitures', 0, '2022-08-23 23:03:26', '2022-08-23 23:03:26');
INSERT INTO ecommerce.product_category VALUES (2147483642, 'Garden Decor', 1, '2022-08-23 23:03:26', '2022-08-23 23:03:26');
INSERT INTO ecommerce.product_category VALUES (2147483643, 'Wall Decor', 2, '2022-08-23 23:03:26', '2022-08-23 23:03:26');
INSERT INTO ecommerce.product_category VALUES (2147483644, 'Lamps', 3, '2022-08-23 23:03:26', '2022-08-23 23:03:26');



INSERT INTO ecommerce.users VALUES (2147483645, true, 'KukatPally Phase 19, Hyderabad', 'admin@gmail.com', 'Admin', '$2a$10$LiBwO43TpKU0sZgCxNkWJueq2lqxoUBsX2Wclzk8JQ3Ejb9MWu2Xy', '1234567890', 'ROLE_MANAGER');


DROP TABLE IF EXISTS `hibernate_sequence`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hibernate_sequence` (
  `next_val` bigint DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

insert into hibernate_sequence (next_val) values (0);
INSERT INTO ecommerce.product_info VALUES ('B0004',0,'2018-03-10 10:39:29','Made from selected Non Woven cloth cover, high quality Galvanized iron pipe and PP Plastic Connectors, this portable storage closet will meet your long term storage needs.','https://m.media-amazon.com/images/I/818dGIz7sQL._SX679_.jpg','Collapsible Wardrobe',30.00,1,1,'2022-08-30 10:19:37');
INSERT INTO ecommerce.product_info VALUES ('B0005',0,'2018-03-10 10:40:35','This 3 Tier Shelf is made up of Laminated Engineered Wood (MDF) which is  dust or any stain and spot from the shelf and also this laminated shelf gives a stylish','https://m.media-amazon.com/images/I/41ssT+j70WL._SY300_SX300_.jpg','Wooden Wall Shelves',30.00,0,199,'2022-08-30 09:55:14');
INSERT INTO  ecommerce.product_info VALUES ('B0011',0,'2022-07-21 22:00:39','Computer Table with Drawers','https://www.decornation.in/wp-content/uploads/2020/09/white-computer-desk.jpg','Study Table',30.00,0,95,'2022-07-21 22:00:39');
INSERT INTO  ecommerce.product_info VALUES ('B0012',0,'2022-07-21 22:00:39','Wooden 4 Seater Dininng Table Set','https://www.decornation.in/wp-content/uploads/2020/06/solid-wood-dining-table-1.jpg','Dining Table',20.00,0,195,'2022-07-21 22:00:39');
INSERT INTO  ecommerce.product_info VALUES ('B0013',0,'2022-07-21 22:00:39','Colourful Fabric Chair','https://ii1.pepperfry.com/media/catalog/product/j/a/1100x1210/jamie-fabric-chair-in-pink-colour-by-evok-jamie-fabric-chair-in-pink-colour-by-evok-prnumi.jpg','Fabric Chair',10.00,1,0,'2022-08-30 10:05:00');
INSERT INTO  ecommerce.product_info VALUES ('B0015',0,'2022-07-21 22:00:39','Wooden Center Table','https://rukminim1.flixcart.com/image/416/416/kph8h3k0/coffee-table/j/b/p/rosewood-sheesham-554954-satkar-wood-furniture-honey-finish-original-imag3p7xamgxuj5s.jpeg?q=70','Center Table',30.00,0,198,'2022-08-30 10:42:18');
INSERT INTO  ecommerce.product_info VALUES ('C0001',2,'2018-03-10 12:09:41','Perfect design.each one is unique and beautifu,designed with adhesive sticker to help attach to the wall','https://images.pexels.com/photos/1648768/pexels-photo-1648768.jpeg?cs=srgb&dl=pexels-victoria-borodinova-1648768.jpg&fm=jpg','Tree Wall Art',10.00,0,110,'2022-08-30 09:44:59');
INSERT INTO  ecommerce.product_info VALUES ('C0002',2,'2018-03-10 12:11:51','WHISQ bricks wallpaper features a stunning rich background for your sober walls. Accessorize your home with wall stickers roll that is made from non toxic and environment friendly material.\n','https://5.imimg.com/data5/ANDROID/Default/2020/12/TG/ID/PI/11916711/product-jpeg-500x500.jpg','HIGH QUALITY HORSE WALLPAPER',13.00,0,109,'2022-08-30 10:43:33');
INSERT INTO  ecommerce.product_info VALUES ('C0003',2,'2018-03-10 12:12:46','it\'s not mirror. Wall1ders an Indian Brand and We Sell only genuine Products.','https://m.media-amazon.com/images/I/71vSY7HlXUL._SX679_.jpg','mirror stickers for wall',22.00,0,222,'2022-07-21 23:26:29');
INSERT INTO  ecommerce.product_info VALUES ('C0011',2,'2022-07-21 22:00:39','Iron Titan Leaf Metal Wall Art','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQGZoadPmrX0DDcKDI1lPN4-HbUaCYFAE3SGD6gTbCiLMYyrO893dlkXryW5-6bqGi-t9U&usqp=CAU','Metal Leaf Art',10.00,0,105,'2022-08-30 09:48:47');
INSERT INTO  ecommerce.product_info VALUES ('C0012',2,'2022-07-21 22:00:39','Iron Double Ring In Flower Wall Art','https://www.woodenmood.in/content/images/thumbs/0003279_white-solid-wood-flower-wall-art_800.jpeg','Flower Wall Art',13.00,0,99,'2022-08-30 10:42:18');
INSERT INTO  ecommerce.product_info VALUES ('C0013',2,'2022-07-21 22:00:39','White Flower Wall Art','https://rukminim1.flixcart.com/image/612/612/kpa39u80/stencil/j/w/z/1-beautiful-flower-design-flower-wall-art-stencil-reusable-wall-original-imag3jczxr2crstv.jpeg?q=70','Print +Flower Art',22.00,0,219,'2022-08-30 09:50:41');
INSERT INTO  ecommerce.product_info VALUES ('D0002',3,'2018-03-10 12:08:17','Hiftocraft™ Decorative Wooden Tripod Floor lamp with Natural Jute Beige Shade 3 Tier Shelf for Home Decor, Living Room Study Room','https://images-eu.ssl-images-amazon.com/images/I/51fU6JTeZqL._AC._SR360,460.jpg','Decorative Wooden Tripod Floor lamp',200.00,0,200,'2022-08-30 09:52:16');
INSERT INTO  ecommerce.product_info VALUES ('D0011',3,'2022-07-21 22:00:39','Beige Printed Modern Fabric Table Lamp','https://assets.myntassets.com/dpr_1.5,q_60,w_400,c_limit,fl_progressive/assets/images/18118450/2022/5/2/223da424-40ec-4f5b-ace5-c8ab54dfa4ef1651490613576TableLamps1.jpg','Printed Lamp',100.00,0,100,'2022-08-30 09:53:44');
INSERT INTO  ecommerce.product_info VALUES ('D0012',3,'2022-07-21 22:00:39','White & Black Cluster Milky Glass Ceiling Lamp','https://images.unsplash.com/photo-1540932239986-30128078f3c5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8N3x8bGFtcHxlbnwwfHwwfHw%3D&w=1000&q=80','White & Black Ceiling Lamp',2.00,0,194,'2022-08-30 10:42:18');
INSERT INTO  ecommerce.product_info VALUES ('D0013',3,'2022-07-21 22:00:39','Iconic Table /Desk Lamps for Modern Interiors','https://design-milk.com/images/2021/06/Koncept-Mr.-N-Table-Lamp.jpeg','Modern Interior Lamp',100.00,0,25,'2022-08-30 09:58:07');
INSERT INTO  ecommerce.product_info VALUES ('F0001',1,'2018-03-10 12:15:05','Swan Planter (with Pot) for Home / Garden / Outdoor Decor','https://m.media-amazon.com/images/I/91NStZqxoSL._SX466_.jpg','Swan Planter',4.00,0,58,'2022-08-30 09:59:27');
INSERT INTO  ecommerce.product_info VALUES ('F0002',1,'2018-03-10 12:16:44','Decorative Planters and Pots Garden Decor','https://nextluxury.com/wp-content/uploads/decorative-planters-and-pots-2-Garden-Decor-Ideas.jpg','Decorative Planters',20.00,0,23,'2022-08-30 10:01:08');
INSERT INTO  ecommerce.product_info VALUES ('F0011',1,'2022-07-21 22:00:39','Fence and Wall Garden Decor','https://nextluxury.com/wp-content/uploads/fence-and-wall-decor-2-garden-decor-ideas-mikepyledesign.jpg','Fence Decor',4.00,0,55,'2022-08-30 10:42:18');
INSERT INTO  ecommerce.product_info VALUES ('F0012',1,'2022-07-21 22:00:39','Fire Pit Garden Decor ','https://nextluxury.com/wp-content/uploads/fire-pit-garden-decor-ideas-ironbarkmetaldesign_.jpg','Fire Pit',1.00,1,0,'2022-08-30 10:04:51');
INSERT INTO  ecommerce.product_info VALUES ('F0013',1,'2022-07-21 22:00:39','Garden Statue Decor','https://nextluxury.com/wp-content/uploads/garden-statue-2-Garden-Decor-Ideas-768x1130.jpg','Garden Statue Decor',15.00,1,0,'2022-08-30 10:04:40');

INSERT INTO ecommerce.users VALUES (96,1,'h34,dwarka,delhi','salman@gmail.com','Salman','$2a$10$9uRrR4606NOmO99dZON1EuaiLsMF08qAwI.0rErMXtA8zoU02IbCO','9999955555','ROLE_MANAGER');
INSERT INTO ecommerce.users VALUES (2147483647,1,'poiuytrq','poi@gmail.com','poi kjh','$2a$10$Kjvuh5z1YqBdfhy5dy7ZHOG/cu0wzl8qWYyMA5TbmpRQbamCtLjBG','0099887766','ROLE_CUSTOMER');