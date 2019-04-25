var app = new Vue({
    el: '#app',
    data: {
       txhash:'',
       pageinfo:''
    },
    mounted() {
        var url = new URL(location.href);
        this.txhash = url.searchParams.get("txhash");
        this.gettransactionbyhash();
    },
    methods: {
        gettransactionbyhash() {
            axios.get('http://localhost:8080/transaction/getTransactionInfoByTxhash', {
                params: {
                    txhash: this.txhash
                }
            })
                .then(function (response) {
                    console.log(response);
                    app.pageinfo = response.data;
                })
                .catch(function (error) {
                    console.log(error);
                });
        }

    }
})