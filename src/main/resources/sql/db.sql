CREATE TABLE customers
(
  id serial NOT NULL,
  login text NOT NULL,
  password text NOT NULL,
  name text NOT NULL,
  address text,
  phone text,
  email text NOT NULL,
  credit_card text,
  CONSTRAINT login_name UNIQUE (login),
  CONSTRAINT unique_customer_id UNIQUE (id)
);

CREATE TABLE groups
(
  id serial NOT NULL,
  group_name text NOT NULL,
  group_parent_id integer,
  CONSTRAINT unique_id UNIQUE (id),
  CONSTRAINT unique_group_name UNIQUE (group_name)
);

CREATE TABLE goods
(
  id serial NOT NULL,
  goods_name text NOT NULL,
  price integer NOT NULL,
  amount integer NOT NULL,
  group_id integer,
  CONSTRAINT goods_group_id_fkey FOREIGN KEY (group_id)
      REFERENCES groups (id) MATCH SIMPLE,
  CONSTRAINT unique_goods_id UNIQUE (id)
);

CREATE TABLE orders
(
  id serial NOT NULL,
  customer_id integer,
  delivery_address text NOT NULL,
  shipping_type text NOT NULL,
  order_status text NOT NULL DEFAULT 'NEW'::text,
  create_date date NOT NULL DEFAULT ('now'::text)::date,
  CONSTRAINT orders_customer_id_fkey FOREIGN KEY (customer_id)
      REFERENCES customers (id) MATCH SIMPLE,
  CONSTRAINT unique_order_id UNIQUE (id)
);

CREATE TABLE order_lines
(
  order_id integer,
  goods_id integer,
  amount integer NOT NULL,
  CONSTRAINT order_lines_goods_id_fkey FOREIGN KEY (goods_id)
      REFERENCES goods (id) MATCH SIMPLE ON DELETE CASCADE,
  CONSTRAINT order_lines_order_id_fkey FOREIGN KEY (order_id)
      REFERENCES orders (id) MATCH SIMPLE
);
