--
-- PostgreSQL database dump
--

-- Dumped from database version 15.1 (Ubuntu 15.1-1.pgdg22.04+1)
-- Dumped by pg_dump version 15.1 (Ubuntu 15.1-1.pgdg22.04+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: accounts; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.accounts (
                                 id bigint NOT NULL,
                                 balance numeric(19,2) NOT NULL,
                                 bank_id bigint,
                                 user_id bigint,
                                 card_number character varying(16),
                                 expire_date timestamp without time zone,
                                 remaining_attempts integer,
                                 pin character varying(100),
                                 auth_method character varying(8)
);


ALTER TABLE public.accounts OWNER TO postgres;

--
-- Name: banks; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.banks (
                              id bigint NOT NULL,
                              address character varying(200),
                              code character varying(30) NOT NULL,
                              name character varying(30) NOT NULL
);


ALTER TABLE public.banks OWNER TO postgres;

--
-- Name: hibernate_sequence; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.hibernate_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.hibernate_sequence OWNER TO postgres;

--
-- Name: terminals; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.terminals (
                                  id bigint NOT NULL,
                                  atm_number character varying(10) NOT NULL,
                                  available_cash numeric(19,2) NOT NULL,
                                  bank_id bigint
);


ALTER TABLE public.terminals OWNER TO postgres;

--
-- Name: transactions; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.transactions (
                                     id bigint NOT NULL,
                                     account_id bigint,
                                     transaction_amount integer,
                                     transaction_status character varying(30),
                                     transaction_type character varying(30),
                                     "timestamp" timestamp without time zone
);


ALTER TABLE public.transactions OWNER TO postgres;

--
-- Name: transactions_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.transactions_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.transactions_seq OWNER TO postgres;

--
-- Name: users; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.users (
                              id bigint NOT NULL,
                              description character varying(2000),
                              first_name character varying(30) NOT NULL,
                              last_name character varying(50) NOT NULL,
                              account_id bigint,
                              fingerprint character varying(100)
);


ALTER TABLE public.users OWNER TO postgres;

--
-- Data for Name: accounts; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.accounts (id, balance, bank_id, user_id, card_number, expire_date, remaining_attempts, pin, auth_method) FROM stdin;
4	92600.00	3	4	2345879078321112	2022-06-23 02:10:25	3	Test	0
7	38478.00	5	3	8765342578323421	2024-06-22 19:10:25	3	4321	0
1	136000.00	1	1	9856342578786543	2023-06-23 00:00:00	3	1224	0
2	59038.00	1	2	7164531182231184	2022-12-23 00:00:00	1	$2a$12$G44/zLcMW.LicOx232/opO3sTH73kKPpQS0XmaEBBBnpeCzBASENS	1
\.


--
-- Data for Name: banks; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.banks (id, address, code, name) FROM stdin;
1	Georgia, Tbilisi	110	BOG
2	Georgia, Tbilisi	120	BOG
3	Georgia, Tbilisi	1112	TBC
4	Georgia, Tbilisi	1122	TBC
5	Georgia, Tbilisi	10	Solo
6	Georgia, Tbilisi	3456	Credo
\.


--
-- Data for Name: terminals; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.terminals (id, atm_number, available_cash, bank_id) FROM stdin;
1	100	10000000.00	\N
2	101	50000000.00	\N
3	101	10000.00	\N
\.


--
-- Data for Name: transactions; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.transactions (id, account_id, transaction_amount, transaction_status, transaction_type, "timestamp") FROM stdin;
0	2	5124	SUCCESS	DEPOSIT	2022-12-06 20:12:33
4	1	2000	SUCCESS	DEPOSIT	2022-12-06 00:00:00
5	1	4000	SUCCESS	DEPOSIT	2022-12-06 00:00:00
6	1	0	SUCCESS	WITHDRAW	2022-12-06 00:00:00
7	1	4000	SUCCESS	WITHDRAW	2022-12-06 00:00:00
8	1	4000	SUCCESS	WITHDRAW	2022-12-06 00:00:00
9	1	20000	0	0	2022-12-07 00:00:00
10	1	20000	0	0	2022-12-07 00:00:00
11	2	7804	0	0	2022-12-07 00:00:00
12	2	1234	0	0	2022-12-07 00:00:00
13	2	2000	0	1	2022-12-08 00:00:00
14	2	20000	1	1	2022-12-08 00:00:00
15	2	20000	0	0	2022-12-08 00:00:00
16	2	20000	0	0	2022-12-08 00:00:00
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.users (id, description, first_name, last_name, account_id, fingerprint) FROM stdin;
1	Senior Java Developer	Andrey	Raskinn	\N	\N
3		Slava	Rokotov	\N	\N
4		Denis	Semenov	\N	\N
5		David	Tennant	\N	\N
2	Senior QA Engineer	Veronika	Bronnikova	2	$2a$12$qKNDQjE2rc.kLGJEitONNOcAPjr5lMsyrLnG/ZNNwAXXIbkoueat2
\.


--
-- Name: hibernate_sequence; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.hibernate_sequence', 1, false);


--
-- Name: transactions_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.transactions_seq', 16, true);


--
-- Name: accounts accounts_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT accounts_pkey PRIMARY KEY (id);


--
-- Name: terminals atms_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.terminals
    ADD CONSTRAINT atms_pkey PRIMARY KEY (id);


--
-- Name: banks banks_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.banks
    ADD CONSTRAINT banks_pkey PRIMARY KEY (id);


--
-- Name: transactions id; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT id PRIMARY KEY (id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: transactions account_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.transactions
    ADD CONSTRAINT account_id FOREIGN KEY (account_id) REFERENCES public.accounts(id);


--
-- Name: users account_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT account_id FOREIGN KEY (account_id) REFERENCES public.accounts(id);


--
-- Name: terminals bank_id; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.terminals
    ADD CONSTRAINT bank_id FOREIGN KEY (bank_id) REFERENCES public.banks(id);


--
-- Name: accounts fkb78evw9x9jyy66ld572kl8rgx; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT fkb78evw9x9jyy66ld572kl8rgx FOREIGN KEY (bank_id) REFERENCES public.banks(id);


--
-- Name: accounts fknjuop33mo69pd79ctplkck40n; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.accounts
    ADD CONSTRAINT fknjuop33mo69pd79ctplkck40n FOREIGN KEY (user_id) REFERENCES public.users(id);


--
-- PostgreSQL database dump complete
--

