// new Vue({
//     el: '#app',
//     data: {
//         userInput: '',
//         messages: []
//     },
//     methods: {
//         sendMessage() {
//             if (this.userInput.trim() === '') return;
//
//             // 添加用户消息
//             this.messages.push({
//                 type: 'user',
//                 text: this.userInput
//             });
//
//             // 模拟发送消息到API并获取回复
//             this.getBotResponse(this.userInput);
//
//             // 清空输入框
//             this.userInput = '';
//         },
//         async getBotResponse(message) {
//     try {
//         // 模拟API调用（替换为实际API调用）
//         const response = await fetch('https://api.example.com/chat', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             body: JSON.stringify({ message })
//         });
//
//         const data = await response.json();
//
//         // 添加机器人回复
//         this.messages.push({
//             type: 'bot',
//             text: data.response || '抱歉，我没有理解你的问题。'
//         });
//     } catch (error) {
//         console.error('API调用失败:', error);
//         this.messages.push({
//             type: 'bot',
//             text: '抱歉，我无法连接到服务器。'
//         });
//     }
// }
// }
// });