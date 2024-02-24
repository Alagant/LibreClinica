
function divsByName(name) {
	var divs = jQuery("div[name^='" + name + "']");
	return divs;
}

function enableUndoForRadioButtons() {
	var undoImgs = divsByName('undoradio');
	undoImgs.each(
		function(index, elem){
			var img = jQuery('<img src="images/bt_Restore.gif">');
			jQuery(elem).replaceWith(img);
			jQuery(img).click(function(){
				resetRadioButtons(jQuery(this));
			});
		});
};

function resetRadioButtons(element) {
	// identify the group radio buttons
	var radioGroup = jQuery(element).parent().parent().find("input");
	for (i = 0; i < radioGroup.length; i++) { 
		radioGroup[i].checked = false;  
	}
	radioGroup.change();
};

function updateInputStyles() {
	var divName = 'inputWidth';
	var updateInputStyleDivs = divsByName(divName);
	updateInputStyleDivs.each(
		function(index, elem){
			elem = jQuery(elem);
			var width = elem.attr('name').replace(divName,'');
			var parentTagName = elem.parent().prop('tagName');
			if(parentTagName == 'TH') {
				updateGroupInputStyles(index, elem, width);
			} else {
				updateNonGroupInputStyles(index, elem, width);
			}
		});
}

function nonGroupFormElement(referenceElem) {
	elem = jQuery(referenceElem);
	var formElem = elem.parent().parent().find("input");
	if(!formElem || formElem.length < 1) {
		formElem = elem.parent().parent().find("select");
	}
	return formElem;
}

function updateNonGroupInputStyles(index, elem, width) {
	elem = jQuery(elem);
	// get form field
	var formElem = nonGroupFormElement(elem);
	// set width if the input field is found
	if(formElem) {
		formElem.width(width);
	}
}

function updateGroupInputStyles(index, elem, width){
	elem = jQuery(elem);
	var elemHeader = elem.parent();
	// set width of table header
	elemHeader.width(width);
	// get column index
	var colNr = elemHeader.parent().children().index(elemHeader) + 1;
	// get form field
	var elemInput = elemHeader.parent().parent().parent().children('tbody').children('tr').children('td:nth-child(' + colNr + ')').children('input');
	if(elemInput.length < 1) {
		elemInput = elemHeader.parent().parent().parent().children('tbody').children('tr').children('td:nth-child(' + colNr + ')').children('select');
	}
	// set width if the input field is found
	if(elemInput) {
		elemInput.css({'width':'' + width + 'px'});
	}
}

function configureToolTips() {
	var toolTipDivs = jQuery("div[id^='tooltip']");
	toolTipDivs.each(
		function(index, elem){
			// move the element in a surounding container div
			elem = jQuery(elem);
			var elemId = elem.attr('id');
			elem.removeAttr('id');

			var newElem = jQuery('<div style="display:none;"></div>');
			newElem.attr('id',elemId);

			elem.replaceWith(newElem);
			elem.appendTo(newElem);
			elem.show();

			// connect the tooltip with the trigger element
			var elemTrigger = jQuery("span[name^='" + elemId + "']");
			if(elemTrigger) {
				var elemTriggerHtml = elemTrigger.html();
				var newElemTrigger = jQuery('<a href="javascript:void(0)" onmouseover=\'TagToTip("' + elemId + '")\' onmouseout="UnTip()">(?)</a>');
				if(!(typeof elemTriggerHtml === 'undefined')) {
					newElemTrigger.html(elemTriggerHtml);
				}
				elemTrigger.replaceWith(newElemTrigger);
			}
		}
	);
}

function copyValue(divName, value){
	var divs = divsByName(divName);
	divs.each(
		function(index, referenceElem){
			referenceElem = jQuery(referenceElem);
			var elem = nonGroupFormElement(referenceElem)
			setElementValue(elem, value);
		});
}

function setElementValue(targetElem, val) {
    if(targetElem) {
	    if(targetElem.is(':radio')) {
	      if(val != null) {
		targetElem.val([val]);
	      } else {
		targetElem.attr('checked', false);
	      }
	      targetElem.change();
	    } else {
	      targetElem.val(val);
	      targetElem.change();
	    }
    }
}

function getSubjectID(){
	var subjectID = jQuery("#centralContainer").find("table:first").find("tbody:first").children("tr:nth-child(1)").children("td:nth-child(2)").children("h1").text();
	subjectID = jQuery.trim(subjectID);
	return subjectID;
}

function copySubjectID(){
	var divName = 'copySubjectID';
	var subjectID = getSubjectID();
	copyValue(divName, subjectID);
}

function setReadOnly() {
	var divName = 'readOnly';
	var divs = divsByName(divName);
	divs.each(
		function(index, elem){
			elem = jQuery(elem);
			disableElement(elem);
		});
}

function getBirth(){
	var birth = jQuery(".tablebox_center").find("tbody:first").children("tr:nth-child(3)").children("td:nth-child(4)").text();
	birth = jQuery.trim(birth);
	return birth;
}

function copyBirth(){
	var divName = 'copyBirth';
	var birth = getBirth();
	copyValue(divName, birth);
}

function getGender() {
	var gender = jQuery(".tablebox_center").find("tbody:first").children("tr:nth-child(1)").children("td:nth-child(4)").text();
	gender = jQuery.trim(gender);
	return gender
}

function copyGender() {
	var divName = 'copyGender';
	var gender = getGender();
	copyValue(divName, gender);
}

function disable(targetId) {
  var targetElem = nonGroupFormElement(jQuery(targetId));
  disableElement(targetElem);
}

function disableElement(targetElem) {
  targetElem.attr('readonly',true);
  targetElem.css("background-color", "#E6E6E6");
  targetElem.click(function(event){event.preventDefault();});

  var inputID = targetElem.attr('id');
  if(inputID) {
	  var anchorID = inputID.replace('input','anchor');
	  jQuery("#" + anchorID).each(function(index, elem){elem.remove();});
  }
}

function enableUpdateTargetOnChange(triggerId, targetId, calculation) {
  // var triggerElem = jQuery(triggerId).parent().parent().find("input");
  // var targetElem = jQuery(targetId).parent().parent().find("input");
  var triggerElem = nonGroupFormElement(jQuery(triggerId));
  var targetElem = nonGroupFormElement(jQuery(targetId));
  triggerElem.change(function() {
    var result = calculation(triggerElem.val());
    setElementValue(targetElem, result);
  });
}

function requestValue(studySubjectOID, studyOID, eventOID, itemOID, calculationFunction) {
  var url = "rest/clinicaldata/xml/view/" + studyOID + "/" + studySubjectOID + "/" + eventOID + "/*";
  var successFunction = function(xml) {
    console.log('SUCCESS FUNCTION');

    
    var studySubjectOID = this['studySubjectOID'];
    var subjectID = this['subjectID'];
    var eventOID = this['eventOID'];
    var itemOID = this['itemOID'];
    var calculationFunction = this['calculation'];

    var subjectCriteria = null;
    if(studySubjectOID == '*') {
      subjectCriteria = 'OpenClinica:StudySubjectID="' + subjectID + '"';
    } else {
      subjectCriteria = 'SubjectKey="' + studySubjectOID + '"';
    }
    // var searchPath = '/ODM/SubjectData[' + subjectCriteria + ']/StudyEventData[StudyEventOID="' + eventOID + '"]/*/ItemData[ItemOID="' + itemOID + '"]';

    odm = jQuery(xml);
    jQuery(xml).find('ODM').find('SubjectData[' + subjectCriteria + ']').find('StudyEventData[StudyEventOID="' + eventOID + '"]').find('ItemData[ItemOID="' + itemOID + '"]').each(function(){
      var value = jQuery(this).attr('Value');
      calculationFunction(value);
    });
  };

  var subjectID = null;
  if(studySubjectOID == '*') {
    subjectID = getSubjectID();
    url = 'rest/clinicaldata/xml/view/' + studyOID + '/*/*/*';
  }

  jQuery.ajax({
		type: "GET",
		url: url,
		dataType: "xml",
		success: successFunction,
    studySubjectOID: studySubjectOID,
    subjectID: subjectID,
    studyOID: studyOID,
    eventOID: eventOID,
    itemOID: itemOID,
    calculation: calculationFunction
  });
}

function enableRadioMessage(containerId, value) {
	var msgContainer = jQuery(containerId);
	var radiosToCheck = msgContainer.parent().parent().find("input");
	radiosToCheck.change(function(){setMessage(radiosToCheck, msgContainer, value);});
	setMessage(radiosToCheck, msgContainer, value);
}

function setMessage(radiosToCheck, msgContainer, value) {
	for (var i = 0; i < radiosToCheck.length; i++) {
		if (radiosToCheck[i].checked) {
			var valueToCheck = radiosToCheck[i].value;
			break;
		}
	}
	if (valueToCheck && (valueToCheck == value)){
		msgContainer.show();
	}
	else {
		msgContainer.hide();
	}
}

jQuery(document).ready(enableUndoForRadioButtons);
jQuery(document).ready(updateInputStyles);
jQuery(document).ready(configureToolTips);
jQuery(document).ready(copySubjectID);
jQuery(document).ready(copyBirth);
jQuery(document).ready(copyGender);
jQuery(document).ready(setReadOnly);
jQuery(document).ready();
jQuery.noConflict();
