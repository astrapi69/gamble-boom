
CREATE TRIGGER draws_if_modified_trg
    AFTER INSERT OR UPDATE OR DELETE ON draws
    FOR EACH ROW EXECUTE PROCEDURE audit.if_modified_func();

CREATE TRIGGER draws_lottery_numbers_if_modified_trg
    AFTER INSERT OR UPDATE OR DELETE ON draws_lottery_numbers
    FOR EACH ROW EXECUTE PROCEDURE audit.if_modified_func();
