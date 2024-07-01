<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Wind Recruiter - ENTRETIEN TECHNIQUE - </title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
</head>
<body style="margin: 0; padding: 3%;">
<div class="container">
    <table align="center" border="0" cellpadding="0" cellspacing="0" width="1000px" style="border-collapse: collapse;">
        <!-- Header -->
        <tr>
            <td bgcolor="#1c1c2b" style="color:white; padding: 2%" width="500px" >
                <span style="text-transform:uppercase;font-size: 32px" > Rapport Final  </span>

            </td>
            <td bgcolor="#1c1c2b" style="color:white; padding: 2%; text-align: right;" width="500px">
                <img src="C:\Users\DELL\Desktop\stage pfe\WindHiring\WindPFE\demo\src\main\resources\static\LogoWindERP.svg" width="150px" height="150px"/>
            </td>
        </tr>
        <tr>
            <td bgcolor="#d3d3d3" style="color:black; padding: 2%" width="500px" >
                <span style="text-transform:uppercase;font-size: 32px" ><img src="https://cdn-icons-png.flaticon.com/512/1946/1946429.png" width="30px" height="30px"/>  ${candidatefirstname} ${cadidatelastname} </span>
                <h4>${email}</h4>
                <h4>  <img src="https://cdn-icons-png.flaticon.com/512/2370/2370264.png" width="20px" height="20px"/>  date de Test : ${date}</h4>

            </td>
            <td bgcolor="#d3d3d3" style="color:black; padding-left:20%" width="500px">
                <span style="font-size: 18px"> <img src="https://cdn-icons-png.flaticon.com/512/3593/3593901.png" width="20px" height="20px" /> Success Rate: </span>
                <span style="font-size: 18px"><i class="fa fa-check-square" aria-hidden="true"></i> ${rate} % </span>
                <br></br>

                <span style="font-size: 18px"><span style="color: black"> <img src="https://cdn-icons-png.flaticon.com/512/25/25643.png" height="15px" width="15px" /> Correct Answers Count:  </span>${correctCount}   </span>
                <br></br>
                <span style="font-size: 18px"> <img src="https://cdn-icons-png.flaticon.com/512/177/177425.png" height="15px" width="15px" /><i class="fa fa-bullseye" aria-hidden="true"></i><span style="color:black"> Score Total :  </span>${scorefinal}</span>
<br></br>

                <span style="font-size: 18px"> <img src="https://cdn-icons-png.flaticon.com/512/177/177425.png" height="15px" width="15px" /><i class="fa fa-bullseye" aria-hidden="true"></i><span style="color:black"> Score final :  </span>${Score}</span>
            </td>
        </tr>

        <!-- Content -->
        <#list questions as question>
            <tr style="border-bottom: 1px solid black">
                <td bgcolor="#ffffff" style="padding: 30px 30px 30px 30px;" width="500px">
                    <span style="color: #1c1c2b "><strong>Question ${question_index + 1} : </strong></span>
                    <br />
                    <span> Question Body: ${question.questionBody} </span>
                    <br />
                    <br></br>
                </td>
                <td bgcolor="#ffffff" style="color:black; padding-left:20%" width="500px">
                    <br />
                    <span style="font-size: 16px"> Question Score : ${question.score}</span>
                    <br />
                    <br />
                    <span style="font-size: 16px"> Verified answer:
            <#if reponseVerifiees?has_content>
                <#if reponseVerifiees[question_index]??>
                    <#if reponseVerifiees[question_index]>
                        <span style="color: green">True</span>
                    <#else>
                        <span style="color: red">False</span>
                    </#if>
                <#else>
                    <span style="color: gray">Pas encore vérifiée</span>
                </#if>
            <#else>
                <span style="color: gray">Pas encore vérifiée</span>
            </#if>
            </span>
                </td>

            </tr>
        </#list>

    </table>
</div>

</body>
</html>
