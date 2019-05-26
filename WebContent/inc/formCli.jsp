	  <div class="newClient">
	  	<label for="nameClient">Name <span class="requis">*</span></label>
		<input type="text" id="nameClient" name="nameClient" value="<c:out value='${client.name }' />" size="20" maxlength="20" />
		<span class="erreur" >${form.errors['nameClient'] }</span>
		 <br />
		                    
		<label for="firstnameClient">Firstname </label>
		<input type="text" id="prenomClient" name="firstnameClient" value="<c:out value='${client.firstname }' />" size="20" maxlength="20" />
		<span class="erreur" >${form.errors['firstnameClient'] }</span>
		<br />
		
		<label for="addressClient">Delivery Address <span class="requis">*</span></label>
		<input type="text" id="adresseClient" name="addressClient" value="<c:out value='${client.address }' />" size="20" maxlength="20" />
		<span class="erreur" >${form.errors['addressClient'] }</span>
		<br />
			    
		<label for="phoneClient">Phone <span class="requis">*</span></label>
		<input type="text" id="phoneClient" name="phoneClient" value="<c:out value='${client.phone }' />" size="20" maxlength="20" />
		<span class="erreur" >${form.errors['phoneClient'] }</span>
		<br />
		                    
		<label for="emailClient">Email address</label>
		<input type="email" id="emailClient" name="emailClient" value="<c:out value='${client.email }' />" size="20" maxlength="60" />
		<span class="erreur" >${form.errors['emailClient'] }</span>
		<br /> 
		
		<label for="imageClient">Image</label>
		<input type="file" id="imageClient" name="imageClient" value="<c:out value='${client.image }' />" size="20" maxlength="60" />
		<span class="erreur" >${form.errors['imageClient'] }</span>
		<br />   
	  </div>  
	    
	    