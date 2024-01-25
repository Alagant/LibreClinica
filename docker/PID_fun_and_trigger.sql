CREATE OR REPLACE FUNCTION public.actualizar_pid()
 RETURNS trigger
 LANGUAGE plpgsql
AS $function$
BEGIN
UPDATE study_subject
SET secondary_label = CONCAT('38-30-', LPAD(NEW.subject_id::TEXT, 4, '0'))
WHERE subject_id = NEW.subject_id;
RETURN NEW;
END;
$function$
;

CREATE TRIGGER trigger_actualizar_PID
    AFTER INSERT ON study_subject
    FOR EACH ROW
    EXECUTE FUNCTION actualizar_PID();
