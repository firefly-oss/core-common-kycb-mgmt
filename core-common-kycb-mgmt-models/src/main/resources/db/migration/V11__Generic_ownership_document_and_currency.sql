-- Beneficial ownership evidence is not a Spanish "titularidad real" document.
-- Money amounts use an ISO currency code.

ALTER TABLE ubo RENAME COLUMN titularidad_real_document TO beneficial_ownership_document;

COMMENT ON COLUMN ubo.beneficial_ownership_document IS 'Reference to the document that evidences beneficial ownership.';

DO $$
DECLARE
    r RECORD;
BEGIN
    FOR r IN
        SELECT table_name, column_name
        FROM information_schema.columns
        WHERE table_schema = current_schema()
          AND data_type = 'text'
          AND column_name IN ('currency', 'currency_iso_code')
    LOOP
        EXECUTE format(
            $f$ALTER TABLE %I ALTER COLUMN %I TYPE VARCHAR(3)
               USING CASE
                   WHEN %I IS NULL THEN NULL
                   WHEN %I ~ '^[A-Za-z]{3}$' THEN UPPER(%I)
                   ELSE NULL
               END$f$,
            r.table_name, r.column_name, r.column_name, r.column_name, r.column_name);
    END LOOP;
END $$;
