create sequence progress_data_id_generator start with 10 increment by 1;

create table progress_data (id number(19,0) not null, json_data clob, lab_id varchar2(255), step_id varchar2(255), timestamp date, tracking_id varchar2(255), primary key (id));
