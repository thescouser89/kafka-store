--
-- JBoss, Home of Professional Open Source.
-- Copyright 2019 Red Hat, Inc., and individual contributors
-- as indicated by the @author tags.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
-- http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

BEGIN transaction;
    create sequence buildstagerecord_seq;
    select id from buildstagerecord order by id desc limit 1;
    -- somehow i can't run a subquery in the command below. so the value has to be substituted manually
    alter sequence buildstagerecord_seq restart with ${value from previous query};
COMMIT;
