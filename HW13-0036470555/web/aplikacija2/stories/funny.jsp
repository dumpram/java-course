<%@page import="java.awt.Color" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<%
String col = "#" + Integer.toString(Integer.valueOf((int)(Math.random()*0xFFFFFF)), 16);
session.setAttribute("fontColor", col);
%>
<body style="background-color:<%= session.getAttribute("pickedBgCol") %>">
<font color = <%= session.getAttribute("fontColor") %>>
	Idu Rus, Šveđan, Ukrajinac, Finac, Norvežan,
	Danac, Nijemac, Poljak, Čeh, Slovak, Mađar, Austrijanac, Švicarac,
	Francuz, Danac, Nizozemac, Španjolac, Portugalac, Andoržanin, Alžirac,
	Markoanac, Tunižanin, Egipćanin, Sudanac, Peruanac, Čileanac,
	Bolivijanac, Urugvajac, Argentinac, Brazilac, ObalaSlonovaKostanac,
	Amerikanac, Meksikanac, GvinejaBisoanac, Malac, Iranac, Iračanin,
	UjedinjeniArapskiEmiračanin, Kuvajčanin, SaudijskiArabijac, Australac,
	Ocenaograf, Oceanac i Hrvat. Kaže Čileanac Brazilcu i namignu na Hrvata? Kaže Norvažanin 
	što namiguješ Brazilče? Kaže na to skoči francuz ljuto i namignu englezu do sebe. Gleda
	to Irac i nije mu nešto jasno. A na to će razrugači oči danac. E kaže moj finče? A rus
	negdje šuti, sjedi. A kaže Mongolijanac e to kaže to neće biti, jer hrvatu kaže on
	ti bi skočio. Jer hrvat je pao, pa se nasmijo Mađar, a na to kaže brazilac ircu: vidiš
	ti kako ti slaveni? A kaže Talijan: koji slaveni!? </font>
	</body>
</html>