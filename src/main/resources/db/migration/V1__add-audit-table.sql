-- create a schema named "audit"
create schema audit;
revoke create on schema audit from public;

create table audit.logged_actions (
    schema_name text not null,
    table_name text not null,
    user_name text,
    action_tstamp timestamp with time zone not null default current_timestamp,
    action TEXT NOT NULL check (action in ('I','D','U')),
    original_data text,
    new_data text,
    query text
) with (fillfactor=100);

revoke all on audit.logged_actions from public;

-- You may wish to use different permissions; this lets anybody
-- see the full audit data. In Pg 9.0 and above you can use column
-- permissions for fine-grained control.
grant select on audit.logged_actions to public;

create index logged_actions_schema_table_idx
    on audit.logged_actions(((schema_name||'.'||table_name)::TEXT));

create index logged_actions_action_tstamp_idx
    on audit.logged_actions(action_tstamp);

create index logged_actions_action_idx
    on audit.logged_actions(action);
