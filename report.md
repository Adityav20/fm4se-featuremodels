
# Report

## Feature Model Analyzer Mandatory Feature

| Test | Status | Reason |
| --- | --- | --- |
| Check Bad FM1Requires | ✅ Passed | - |
| Check Dead Features Bad FM | ✅ Passed | - |
| Check No Dead Features1FFM | ❌ Failed |  Expect mandatory feature car, but got []  |
| Check No Dead Features2FFM | ❌ Failed |  Expect mandatory feature car, but got []  |
| Check Valid Features FM1Dead | ❌ Failed |  Expect mandatory feature car, but got []  |
## Feature Model Analyzer Translation

| Test | Status | Reason |
| --- | --- | --- |
| XORFeature | ❌ Failed |  Expect consistent FM, but got inconsistent  |
| Mandatory Feature | ❌ Failed |  Expect consistent FM, but got inconsistent  |
| Single Feature | ❌ Failed |  Expect consistent FM, but got inconsistent  |
| ORFeature | ❌ Failed |  Expect consistent FM, but got inconsistent  |
| Parent Child | ❌ Failed |  Expect consistent FM, but got inconsistent  |
## Products Preserved

| Test | Status | Reason |
| --- | --- | --- |
| Completely Different Feature Model | ✅ Passed | - |
| Product Preserved Single Feature | ❌ Failed |  Expect first FM set of products to be subset of second FM, but got false  |
| Mandatory And Requires | ❌ Failed |  Expect first FM set of products to be subset of second FM, but got false  |
## Limboole Executor

| Test | Status | Reason |
| --- | --- | --- |
| Limboole Working | ✅ Passed | - |
## Feature Model Analyzer Dead Feature

| Test | Status | Reason |
| --- | --- | --- |
| Check Bad FM1Requires | ❌ Failed |  Expect dead feature pedals, but got []  |
| Check Dead Features Bad FM | ❌ Failed |  Expect Root feature is dead, but got []  |
| Check No Dead Features1FFM | ✅ Passed | - |
| Check No Dead Features2FFM | ✅ Passed | - |
| Check Valid Features FM1Dead | ❌ Failed |  Expect dead feature pedals, but got []  |
## Feature Model Analyzer

| Test | Status | Reason |
| --- | --- | --- |
| Check Consistent Bad FM | ✅ Passed | - |
| Check Consistent Car FM | ❌ Failed |  Expect consistent FM, but got inconsistent  |
| Check Dead Features Bad FM | ❌ Failed |  Expec root feature is dead, but got []  |
| Check Dead Features Car FM | ✅ Passed | - |
