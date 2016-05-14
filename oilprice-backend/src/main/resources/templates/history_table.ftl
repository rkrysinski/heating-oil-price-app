<table>
	<#list prices as price>
	  <tr>
	    <td style="padding-right: 10px;">${price.date}</td>
	    <td>${price.value}</td>
	  </tr>	
	</#list>
</table>