pm.test("Successful POST request", function () {
    pm.expect(pm.response.code).to.be.oneOf([200]);
});
if(pm.response.to.have.status(200)){
    var jsonData=JSON.parse(responseBody);
    pm.environment.set("token", jsonData.token);
}

