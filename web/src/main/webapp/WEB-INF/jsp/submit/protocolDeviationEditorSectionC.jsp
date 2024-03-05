<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="section-c" style="display: flex; flex-direction: column">
    <h2>C. Incident Description</h2>
    <ol>
        <li>
            1. <span class="question">What is the reason for the protocol deviation (check all that apply)?</span>
            <div class="detail">
                <input type="checkbox" name="item_c_1_1" value="1"> Participant illness
                <input type="checkbox" name="item_c_1_2" value="1"> Participant unable to comply
                <input type="checkbox" name="item_c_1_3" value="1"> Participant refusal
                <input type="checkbox" name="item_c_1_4" value="1"> Study staff error
                <input type="checkbox" name="item_c_1_5" value="1"> Pharmacist error
                <input type="checkbox" name="item_c_1_6" value="1"> Laboratorian error
                <input type="checkbox" name="item_c_1_7" value="1"> Investigator/study decision
                <input type="checkbox" name="item_c_1_8" value="1"> Shipment error
                <input type="checkbox" name="item_c_1_9" value="1"> Other, specify
                <input type="text" name="item_c_1_10">
            </div>
        </li>
        <li>
            2. <span class="question">Provide a detailed description of the incident:</span>
            <textarea name="item_c_2"></textarea>
        </li>
    </ol>
</div>