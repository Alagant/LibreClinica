<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="section-g" style="display: flex; flex-direction: column">
    <h2>G. Reporting Incident</h2>
    <div class="formlabel">
        1. <strong>How did the TBTC become aware of the incident?</strong>
        <input type="radio" name="item_g_1" value="1">Site staff reported incident
        <input type="radio" name="item_g_1" value="2">QA Checks
        <input type="radio" name="item_g_1" value="3">Other
    </div>
    <div class="formlabel">
        2. <strong>CDC IRB Reporting Category:</strong>
        <input type="checkbox" name="item_g_2" value="1">
            Unanticipated problem(s) involving risks to subjects or others
        <input type="checkbox" name="item_g_2" value="1">
            Serious or continuing noncompliance with regulations or IRB requirements
            (e.g. breach of protocol)
        <input type="checkbox" name="item_g_3" value="1">
        Suspension or termination (for reasons other than expiration)
        [if termination, also submit 0.1253]
        <input type="checkbox" name="item_g_4" value="1">
        Other incidents as specified in the protocol or requested by the IRB
    </div>
    <div class="formlabel">
        3. <strong>This incident changes the harm-benefit profile of the research?</strong>
        <input type="radio" name="item_g_3" value="1">Yes
        <input type="radio" name="item_g_3" value="0">No
    </div>
    <div class="formlabel">
        4. <strong>The protocol has been or needs to be revised [also submit from 0.1252]?</strong>
        <input type="radio" name="item_g_4" value="1">Yes
        <input type="radio" name="item_g_4" value="0">No
    </div>
    <div class="formlabel">
        5. <strong>The consent process or document has been or needs to be revised [also submit form 0.1252]?</strong>
        <input type="radio" name="item_g_5" value="1">Yes
        <input type="radio" name="item_g_5" value="0">No
    </div>
    <div class="formlabel">
        6. <strong>Date CRB became aware of the incident</strong>
        <input type="text" name="item_g_6">
    </div>
    <div class="formlabel">
        <table>
            <thead>
                <tr>
                    <th>Invalid email notification</th>
                    <th>Submision of CDC HRPO form 1254</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        To study officer:
                        <input type="text" name="item_g_6_1_a">
                        <input type="checkbox" name="item_g_6_1_b"> Not applicable
                    </td>
                    <td>
                        <input type="text" name="item_g_6_1_c">
                    </td>
                </tr>
                <tr>
                    <td>
                        Study officer to branch chief:
                        <input type="text" name="item_g_6_2_a">
                        <input type="checkbox" name="item_g_6_2_b"> Not applicable
                    </td>
                    <td>
                        <input type="text" name="item_g_6_2_c">
                    </td>
                </tr>
                <tr>
                    <td>
                        Branch chief to ADS:
                        <input type="text" name="item_6_3_a">
                        <input type="checkbox" name="item_g_6_3_b"> Not applicable
                    </td>
                    <td>
                        <input type="text" name="item_g_6_3_c">
                    </td>
                </tr>
                <tr>
                    <td>
                        ADS to HRPO:
                        <input type="text" name="item_6_4_a">
                        <input type="checkbox" name="item_g_6_4_b"> Not applicable
                    </td>
                    <td>
                        <input type="text" name="item_g_6_4_c">
                    </td>
                </tr>
            </tbody>
        </table>
        <div class="formlabel">
            7. <strong>HRPO Action Number:</strong>
            <input type="text" name="item_g_7">
        </div>
        <div class="formlabel">
            8. <strong>HRPO Assigned Incident Number:</strong>
            <input type="text" name="item_g_8">
        </div>
        <div class="formlabel">
            9. <strong>Comments by CDC</strong>
            <textarea name="item_g_9"></textarea>
        </div>
    </div>
</div>