<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="section-g" style="display: flex; flex-direction: column">
    <h2>G. Reporting Incident</h2>
    <ol>
    <li>
        1. <span class="question">How did the TBTC become aware of the incident?</span>
        <div class="detail">
            <input type="radio" name="item_g_1" value="1">Site staff reported incident
            <input type="radio" name="item_g_1" value="2">QA Checks
            <input type="radio" name="item_g_1" value="3">Other
        </div>
    </li>
    <li>
        2. <span class="question">CDC IRB Reporting Category:</span>
        <div class="detail">
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
    </li>
    <li>
        3. <span class="question">This incident changes the harm-benefit profile of the research?</span>
        <div class="detail">
        <input type="radio" name="item_g_3" value="1">Yes
        <input type="radio" name="item_g_3" value="0">No
        </div>
    </li>
    <li>
        4. <span class="question">The protocol has been or needs to be revised
        [also submit from 0.1252]?</span>
        <div class="detail">
        <input type="radio" name="item_g_4" value="1">Yes
        <input type="radio" name="item_g_4" value="0">No
        </div>
    </li>
    <li>
        5. <span class="question">The consent process or document has been or needs
        to be revised [also submit form 0.1252]?</span>
        <div class="detail">
        <input type="radio" name="item_g_5" value="1">Yes
        <input type="radio" name="item_g_5" value="0">No
        </div>
    </li>
    <li>
        6. <strong>Date CRB became aware of the incident</strong>
        <div class="detail">
        <input type="text" name="item_g_6">
        </div>
    </li>
    <li>
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
        <li>
            7. <span class="question">HRPO Action Number:</span>
                <div class="detail">
                    <input type="text" name="item_g_7">
                </div>
        </li>
        <li>
            8. <span class="question">HRPO Assigned Incident Number:</span>
            <div class="detail">
                    <input type="text" name="item_g_8">
            </div>
        </li>
        <li>
            9. <span class="question">Comments by CDC</span>
            <div class="detail">
                <textarea name="item_g_9"></textarea>
            </div>
        </li>
    </ol>
</div>