<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


<fmt:setBundle basename="org.akaza.openclinica.i18n.words" var="resword"/>
<fmt:setBundle basename="org.akaza.openclinica.i18n.notes" var="restext"/>


<jsp:include page="../include/submit-header.jsp"/>
<!-- move the alert message to the sidebar-->
<jsp:include page="../include/sideAlert.jsp"/>

<link rel="stylesheet" href="includes/jmesa/jmesa.css" type="text/css">

<script type="text/JavaScript" language="JavaScript" src="includes/jmesa/jquery.min.js"></script>
<script type="text/JavaScript" language="JavaScript" src="includes/jmesa/jquery.jmesa.js"></script>
<script type="text/JavaScript" language="JavaScript" src="includes/jmesa/jmesa.js"></script>
<%-- <script type="text/JavaScript" language="JavaScript" src="includes/jmesa/jmesa-original.js"></script> --%>
<script type="text/javascript" language="JavaScript" src="includes/jmesa/jquery.blockUI.js"></script>

<script type="text/javascript" language="JavaScript" src="includes/jmesa/jquery-migrate-1.1.1.js"></script>

<script type="text/javascript">
    function onInvokeAction(id,action) {
        if(id.indexOf('findSubjects') == -1)  {
            setExportToLimit(id, '');
        }
        createHiddenInputFieldsForLimitAndSubmit(id);
    }
    function onInvokeExportAction(id) {
        var parameterString = createParameterStringForLimit(id);
        location.href = '${pageContext.request.contextPath}/ListStudySubjects?'+ parameterString;
    }

    jQuery(document).ready(function() {
        jQuery("#add-subject").click(()=> {
            var newSubject = jQuery("#new-subject").val();
            var option = jQuery('#new-subject option[value="'+newSubject+'"]');
            jQuery(option).css('display', 'none');
            jQuery("#new-subject").val("");


            jQuery("#subjects-added").append(
                '<div class="protocol-deviation-subject">'+
                    '<div style="flex: 1">'+
                        '<input type="hidden" name="subjects[]" value="'+newSubject+'"/>'+
                        $(option).text()+
                    '</div>'+
                    '<div class="remove-item">'+
                        '<a href="javascript:" class="remove-subject">Remove</a>'+
                    '<div>'+
                '</div>');
        });

        jQuery('#addSubject').click(function() {
            jQuery.blockUI({ message: jQuery('#protocol-deviation-editor'), css:{left: "300px", top:"10px" } });
        });

        jQuery('.protocol-deviation-editor').click(function(e) {
            const protocolId = $(e.target).data('id');
            jQuery.ajax({
                url: "${pageContext.request.contextPath}/ProtocolDeviations?action=get&pdid="+
                        protocolId,
                success: function(response) {
                    jQuery('input[name="protocol_deviation_id"]').val(response.protocolDeviationId);
                    jQuery('input[name="item_a_1"][value="'+response.itemA1+'"]').prop('checked', true);
                    jQuery('input[name="item_a_2"][value="'+response.itemA2+'"]').prop('checked', true);
                    jQuery('input[name="item_a_3"]').val(response.itemA3);
                    jQuery('input[name="item_a_4"]').val(response.itemA4);
                    jQuery('input[name="item_a_5"]').val(response.itemA5);
                    jQuery('input[name="item_a_6"][value="'+response.itemA6+'"]').prop('checked', true);
                    jQuery('input[name="item_a_7"][value="'+response.itemA7+'"]').prop('checked', true);
                    jQuery('input[name="item_a_7_1"]').val(response.itemA7_1);
                    jQuery('input[name="item_a_8"][value="'+response.itemA8+'"]').prop('checked', true);

                    jQuery('input[name="item_b_1"][value="'+response.itemB1+'"]').prop('checked', true);
                    jQuery('input[name="item_b_2"][value="'+response.itemB2+'"]').prop('checked', true);
                    jQuery('input[name="item_b_3"][value="'+response.itemB3+'"]').prop('checked', true);
                    jQuery('input[name="item_b_4"][value="'+response.itemB4+'"]').prop('checked', true);
                    jQuery('input[name="item_b_5"][value="'+response.itemB5+'"]').prop('checked', true);
                    jQuery('input[name="item_b_6"][value="'+response.itemB6+'"]').prop('checked', true);
                    jQuery('input[name="item_b_7"][value="'+response.itemB7+'"]').prop('checked', true);
                    jQuery('input[name="item_b_8"][value="'+response.itemB8+'"]').prop('checked', true);
                    jQuery('input[name="item_b_9"][value="'+response.itemB9+'"]').prop('checked', true);
                    jQuery('input[name="item_b_10"][value="'+response.itemB10+'"]').prop('checked', true);
                    jQuery('input[name="item_b_11"][value="'+response.itemB11+'"]').prop('checked', true);
                    jQuery('input[name="item_b_12"][value="'+response.itemB12+'"]').prop('checked', true);
                    jQuery('input[name="item_b_13"][value="'+response.itemB13+'"]').prop('checked', true);
                    jQuery('input[name="item_b_14"][value="'+response.itemB14+'"]').prop('checked', true);
                    jQuery('input[name="item_b_15"][value="'+response.itemB15+'"]').prop('checked', true);
                    jQuery('input[name="item_b_16"][value="'+response.itemB16+'"]').prop('checked', true);
                    jQuery('input[name="item_b_17"][value="'+response.itemB17+'"]').prop('checked', true);
                    jQuery('input[name="item_b_18"][value="'+response.itemB18+'"]').prop('checked', true);

                    jQuery('input[name="item_c_1_1"]').prop('checked', response.itemC1_1);
                    jQuery('input[name="item_c_1_2"]').prop('checked', response.itemC1_2);
                    jQuery('input[name="item_c_1_3"]').prop('checked', response.itemC1_3);
                    jQuery('input[name="item_c_1_4"]').prop('checked', response.itemC1_4);
                    jQuery('input[name="item_c_1_5"]').prop('checked', response.itemC1_5);
                    jQuery('input[name="item_c_1_6"]').prop('checked', response.itemC1_6);
                    jQuery('input[name="item_c_1_7"]').prop('checked', response.itemC1_7);
                    jQuery('input[name="item_c_1_8"]').prop('checked', response.itemC1_8);
                    jQuery('input[name="item_c_1_9"]').prop('checked', response.itemC1_9);
                    jQuery('input[name="item_c_1_10"]').val(response.itemC1_10);
                    jQuery('textarea[name="item_c_2"]').val(response.itemC2);

                    jQuery('input[name="item_d_1_a"]').val(response.itemD1_A);
                    jQuery('textarea[name="item_d_1_b"]').val(response.itemD2_B);

                    jQuery('textarea[name="item_e_1"]').val(response.itemE1);
                    jQuery('textarea[name="item_e_2"]').val(response.itemE2);
                    jQuery('textarea[name="item_e_3"]').val(response.itemE3);
                    jQuery('textarea[name="item_e_4"]').val(response.itemE4);

                    jQuery('input[name="item_f_1"]').val(response.itemF1);
                    jQuery('input[name="item_f_2"]').val(response.itemF2);
                    jQuery('input[name="item_f_3"]').val(response.itemF3);

                    jQuery('input[name="item_g_1"][value="'+response.itemG1+'"]').prop('checked', true);
                    jQuery('input[name="item_g_2_1"]').prop('checked', response.itemG2_1);
                    jQuery('input[name="item_g_2_2"]').prop('checked', response.itemG2_2);
                    jQuery('input[name="item_g_2_3"]').prop('checked', response.itemG2_3);
                    jQuery('input[name="item_g_2_4"]').prop('checked', response.itemG2_4);
                    jQuery('input[name="item_g_3"][value="'+response.itemG3+'"]').prop('checked', true);
                    jQuery('input[name="item_g_4"][value="'+response.itemG4+'"]').prop('checked', true);
                    jQuery('input[name="item_g_5"][value="'+response.itemG5+'"]').prop('checked', true);
                    jQuery('input[name="item_g_6"]').val(response.itemG6);
                    jQuery('input[name="item_g_6_1_a"]').val(response.itemG6_1_A);
                    jQuery('input[name="item_g_6_1_b"]').prop('checked', response.itemG6_1_B);
                    jQuery('input[name="item_g_6_1_c"]').val(response.itemG6_1_C);
                    jQuery('input[name="item_g_6_2_a"]').val(response.itemG6_2_A);
                    jQuery('input[name="item_g_6_2_b"]').prop('checked', response.itemG6_2_B);
                    jQuery('input[name="item_g_6_2_c"]').val(response.itemG6_2_C);
                    jQuery('input[name="item_g_6_3_a"]').val(response.itemG6_3_A);
                    jQuery('input[name="item_g_6_3_b"]').prop('checked', response.itemG6_3_B);
                    jQuery('input[name="item_g_6_3_c"]').val(response.itemG6_3_C);
                    jQuery('input[name="item_g_6_4_a"]').val(response.itemG6_4_A);
                    jQuery('input[name="item_g_6_4_b"]').prop('checked', response.itemG6_4_B);
                    jQuery('input[name="item_g_6_4_c"]').val(response.itemG6_4_C);
                    jQuery('input[name="item_g_7"]').val(response.itemG7);
                    jQuery('input[name="item_g_8"]').val(response.itemG8);
                    jQuery('textarea[name="item_g_9"]').val(response.itemG9);


                    jQuery('#subjects-added').html('');
                    (response?.subjects || []).forEach((x,i) => {
                        jQuery('#subjects-added').append('<div class="protocol-deviation-subject">'+
                            '<div style="flex: 1">'+
                                '<input type="hidden" name="subjects[]" value="'+ x.id +'"/>'+
                                x.label+
                                '</div>'+
                                '<div class="remove-item">'+
                                '<a href="javascript:" class="remove-subject">Remove</a>'+
                                '<div>'+
                            '</div>'
                        );
                    });
                    jQuery.blockUI({ message: jQuery('#protocol-deviation-editor'), css:{left: "300px", top:"10px" } });
                    console.log(response);
                }
            });
        });

        jQuery('#cancel').click(function() {
            jQuery.unblockUI();
            return false;
        });
    });
</script>


<tr id="sidebar_Instructions_open" style="display: none">
    <td class="sidebar_tab">

        <a href="javascript:leftnavExpand('sidebar_Instructions_open'); leftnavExpand('sidebar_Instructions_closed');"><img src="images/sidebar_collapse.gif" border="0" align="right" hspace="10"></a>

        <b><fmt:message key="instructions" bundle="${resword}"/></b>

        <div class="sidebar_tab_content">

        </div>

    </td>

</tr>
<tr id="sidebar_Instructions_closed" style="display: all">
    <td class="sidebar_tab">

        <a href="javascript:leftnavExpand('sidebar_Instructions_open'); leftnavExpand('sidebar_Instructions_closed');"><img src="images/sidebar_expand.gif" border="0" align="right" hspace="10"></a>

        <b><fmt:message key="instructions" bundle="${resword}"/></b>

    </td>
</tr>
<jsp:include page="../include/sideInfo.jsp"/>

<jsp:useBean scope='session' id='userBean' class='org.akaza.openclinica.bean.login.UserAccountBean'/>
<jsp:useBean scope='request' id='crf' class='org.akaza.openclinica.bean.admin.CRFBean'/>


<h1><span class="title_manage"><fmt:message key="view_subjects_in" bundle="${restext}"/> <c:out value="${study.name}"/></span></h1>

<h1><div class="title_manage">Protocol deviations</div></h1>
<div id="findProtocolDeviationsDiv">
    <form  action="${pageContext.request.contextPath}/ProtocolDeviations">
        ${findProtocolDeviationsHtml}
    </form>
</div>

<div id="protocol-deviation-editor" style="display:none;">
    <c:import url="../submit/addNewProtocolDeviation.jsp">
    </c:import>
</div>



<script type="text/javascript">
    <c:if test="${showOverlay}">
    //jQuery.blockUI({ message: jQuery('#addSubjectForm'), css:{left: "300px", top:"10px" } });
    jQuery.blockUI({ message: jQuery('#protocol-deviation-editor'), css:{left: "300px", top:"10px" } });
    </c:if>
</script>




<jsp:include page="../include/footer.jsp"/>


