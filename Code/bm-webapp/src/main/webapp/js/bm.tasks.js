$(function() {
		$(".hasDatePicker").datepicker({ dateFormat: 'dd-mm-yy' });
		
		$('#startDate').change(function() {
			if($('#startDate').val() != '')
			{
				$( '#endDate' ).datepicker( "option", "minDate", $.datepicker.parseDate('dd-mm-yy', $('#startDate').val()));
			}
			else
			{
				$( '#endDate' ).datepicker( "option", "minDate", null);
			}
		});
});
				