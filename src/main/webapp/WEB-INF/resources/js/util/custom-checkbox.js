$(document).ready(function(){
    _initCheckbox();
});
var targetClass = 'custom-checkbox-bootstrap';

function _initCheckbox() {
    var $inputs = $('input[type="checkbox"].'+targetClass);
    $inputs.each(function(index, input, arr) {
        _init($(input));
    });
}

function _init($input) {
    var id = $input.attr('id');
    if(id === undefined){
        id = _generateId();
        $input.attr('id', id);
    }
    $input.css('display', 'none');
    $input.attr('autocomplete', 'off');
    $input.wrap('<div class="custom-checkbox-wrapper">');
    var wrapper = $input.parent('div');
    var text = $input.attr('text');
    var currentChecked = $input.prop('checked');
    text = text!= undefined?text:'';
    wrapper.append('<label class="form-control custom-checkbox-label custom-checkbox-label-text" for="'+id+'">'+text+'</label><label class="form-control custom-checkbox-icon" for="'+id+'"><span input-id="'+id+'" class="glyphicon" >&nbsp;</span></label>');
    $input.change(function() {
        var checked = $(this).prop('checked');
        var id = $(this).attr('id');
        _setChecked(id, checked);
    });
    _setChecked(id, currentChecked);
}

function _setChecked(id, checked) {
    var $span = $('[input-id="'+id+'"]');
    var $lab = $span.parent('label');
    if(checked) {
        $span.addClass('glyphicon-ok');
        $span.removeClass('glyphicon-remove');
        $lab.addClass('custom-checkbox-checked-icon');
        $lab.removeClass('custom-checkbox-unchecked-icon');
    } else {
        $span.addClass('glyphicon-remove');
        $span.removeClass('glyphicon-ok');
        $lab.addClass('custom-checkbox-unchecked-icon');
        $lab.removeClass('custom-checkbox-checked-icon');
    }
}

var generatorIdIteration = 0;
var prefixId = 'custom-input-checkbox-id-'
function _generateId() {
    return prefixId + (generatorIdIteration++);
}