--
-- PostgreSQL database dump
--

-- Dumped from database version 10.0
-- Dumped by pg_dump version 10.0

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE hibernate_sequence OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE item (
    id integer NOT NULL,
    descricao character varying(255) NOT NULL,
    valor double precision NOT NULL
);


ALTER TABLE item OWNER TO postgres;

--
-- Name: lancamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE lancamento (
    id integer NOT NULL,
    dt_final timestamp without time zone NOT NULL,
    dt_inicial timestamp without time zone NOT NULL,
    observacao character varying(1000),
    vl_total double precision NOT NULL
);


ALTER TABLE lancamento OWNER TO postgres;

--
-- Name: lancamento_item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE lancamento_item (
    lancamento_id integer NOT NULL,
    itens_id integer NOT NULL
);


ALTER TABLE lancamento_item OWNER TO postgres;

--
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY item
    ADD CONSTRAINT item_pkey PRIMARY KEY (id);


--
-- Name: lancamento lancamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY lancamento
    ADD CONSTRAINT lancamento_pkey PRIMARY KEY (id);


--
-- Name: lancamento_item fk104ayas41663df7nfo411pdq6; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY lancamento_item
    ADD CONSTRAINT fk104ayas41663df7nfo411pdq6 FOREIGN KEY (itens_id) REFERENCES item(id);


--
-- Name: lancamento_item fkjkmmsgfc9inqol57twvhbn3o3; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY lancamento_item
    ADD CONSTRAINT fkjkmmsgfc9inqol57twvhbn3o3 FOREIGN KEY (lancamento_id) REFERENCES lancamento(id);


--
-- PostgreSQL database dump complete
--

