/*
  Authors : initappz (Rahul Jograna)
  Website : https://initappz.com/
  App Name : Salon - 2 This App Template Source code is licensed as per the
  terms found in the Website https://initappz.com/license
  Copyright and Good Faith Purchasers © 2023-present initappz.
*/
import { Injectable, NgZone } from '@angular/core';
import { LoadingController, AlertController, ToastController, NavController, MenuController } from '@ionic/angular';
import { Haptics, ImpactStyle } from '@capacitor/haptics';
import { NavigationExtras, Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class UtilService {
  isLoading = false;
  offers: any[] = [
    {
      "title": "First-time client special",
      "desc": "These tags can be used to highlight special promotions or deals to attract customers and increase sales in a salon setting",
      "off": "20",
    },
    {
      "title": "Loyalty program",
      "desc": "These tags can be used to highlight special promotions or deals to attract customers and increase sales in a salon setting",
      "off": "30",
    },
    {
      "title": "Refer-a-friend discount",
      "desc": "These tags can be used to highlight special promotions or deals to attract customers and increase sales in a salon setting",
      "off": "10",
    },
    {
      "title": "Summer hair package",
      "desc": "These tags can be used to highlight special promotions or deals to attract customers and increase sales in a salon setting",
      "off": "40",
    },
    {
      "title": "Group booking deal",
      "desc": "These tags can be used to highlight special promotions or deals to attract customers and increase sales in a salon setting",
      "off": "50",
    },
    {
      "title": "Special occasion package",
      "desc": "These tags can be used to highlight special promotions or deals to attract customers and increase sales in a salon setting",
      "off": "60",
    },
    {
      "title": "Seasonal hair color promotion",
      "desc": "These tags can be used to highlight special promotions or deals to attract customers and increase sales in a salon setting",
      "off": "65",
    },
  ];
  categories: any[] = [
    {
      "name": "Haircuts",
      "image": "assets/images/category/1.png"
    },
    {
      "name": "Styling",
      "image": "assets/images/category/2.png"
    },
    {
      "name": "Texturizing",
      "image": "assets/images/category/3.png"
    },
    {
      "name": "Coloring",
      "image": "assets/images/category/4.png"
    },
    {
      "name": "haircuts",
      "image": "assets/images/category/5.png"
    },
    {
      "name": "styling",
      "image": "assets/images/category/6.png"
    },
    {
      "name": "coloring",
      "image": "assets/images/category/7.png"
    },
    {
      "name": "hair treatments",
      "image": "assets/images/category/8.png"
    },
    {
      "name": "manicures",
      "image": "assets/images/category/9.png"
    },
    {
      "name": "pedicures",
      "image": "assets/images/category/10.png"
    },
    {
      "name": "nail art",
      "image": "assets/images/category/11.png"
    },
    {
      "name": "massages",
      "image": "assets/images/category/12.png"
    },
    {
      "name": "facials",
      "image": "assets/images/category/13.png"
    },
    {
      "name": "shaves",
      "image": "assets/images/category/14.png"
    },
    {
      "name": "beard grooming",
      "image": "assets/images/category/15.png"
    },
    {
      "name": "UV tanning",
      "image": "assets/images/category/16.png"
    },
    {
      "name": "spray tanning",
      "image": "assets/images/category/17.png"
    },
    {
      "name": "Botox",
      "image": "assets/images/category/18.png"
    },
    {
      "name": "fillers",
      "image": "assets/images/category/19.png"
    },
    {
      "name": "laser hair",
      "image": "assets/images/category/20.png"
    },
    {
      "name": "skin rejuvenation",
      "image": "assets/images/category/21.png"
    },
    {
      "name": "contouring",
      "image": "assets/images/category/22.png"
    },
  ];
  salonList: any[] = [
    {
      "name": "Elevate Salon",
      "address": "3788 Chapel Street Sugar Land",
      "cover": "assets/images/salon-cover/1.png",
      "rate": "4.2",
      "distance": "1.2 KM"
    },
    {
      "name": "Luxe Hair Studio",
      "address": "3104 Public Works Drive Chattanooga",
      "cover": "assets/images/salon-cover/2.png",
      "rate": "4.6",
      "distance": "1.2 KM"
    },
    {
      "name": "The Mane Event",
      "address": "864 Lucy Lane Evansville",
      "cover": "assets/images/salon-cover/3.png",
      "rate": "5.0",
      "distance": "1.2 KM"
    },
    {
      "name": "Shear Bliss Salon",
      "address": "837 Nutter Street Kansas City",
      "cover": "assets/images/salon-cover/4.png",
      "rate": "4.8",
      "distance": "1.2 KM"
    },
    {
      "name": "Cutting Edge Hair Design",
      "address": "3241 Willis Avenue Mayo",
      "cover": "assets/images/salon-cover/5.png",
      "rate": "4.3",
      "distance": "1.2 KM"
    },
    {
      "name": "The Strand Studio",
      "address": "4206 Lightning Point Drive Memphis",
      "cover": "assets/images/salon-cover/6.png",
      "rate": "4.1",
      "distance": "1.2 KM"
    },
    {
      "name": "Fringe Benefits Hair Salon",
      "address": "4284 Elk City Road Indianapolis",
      "cover": "assets/images/salon-cover/7.png",
      "rate": "5.0",
      "distance": "1.2 KM"
    },
    {
      "name": "Crowning Glory Salon",
      "address": "4237 Diane Street City Of Commerce",
      "cover": "assets/images/salon-cover/8.png",
      "rate": "4.9",
      "distance": "1.2 KM"
    },
    {
      "name": "The Glam Room",
      "address": "356 Whitman Court Stamford",
      "cover": "assets/images/salon-cover/9.png",
      "rate": "4.5",
      "distance": "1.2 KM"
    },
    {
      "name": "The Tress Effect",
      "address": "4658 Stonecoal Road Toledo",
      "cover": "assets/images/salon-cover/10.png",
      "rate": "3.6",
      "distance": "1.2 KM"
    },
  ];
  userList: any[] = [
    {
      "image": "assets/images/avatar/1.jpg",
      "name": "Richard G. Oneal"
    },
    {
      "image": "assets/images/avatar/2.jpg",
      "name": "Floyd M. Helton"
    },
    {
      "image": "assets/images/avatar/3.jpg",
      "name": "Matthew M. Hernandez"
    },
    {
      "image": "assets/images/avatar/4.jpg",
      "name": "Candice M. Coffey"
    },
    {
      "image": "assets/images/avatar/5.jpg",
      "name": "Terrie R. Cobb"
    },
    {
      "image": "assets/images/avatar/6.jpg",
      "name": "Clarissa C. Wentz"
    },
    {
      "image": "assets/images/avatar/7.jpg",
      "name": "Shirley J. Arnold"
    },
    {
      "image": "assets/images/avatar/8.jpg",
      "name": "Jack R. Applegate"
    },
    {
      "image": "assets/images/avatar/9.jpg",
      "name": "Anita T. Ross"
    },
    {
      "image": "assets/images/avatar/10.jpg",
      "name": "Dianna K. Wadley"
    },
    {
      "image": "assets/images/avatar/11.jpg",
      "name": "Rodney R. Ruddy"
    },
    {
      "image": "assets/images/avatar/12.jpg",
      "name": "Deanna B. Mull"
    },
    {
      "image": "assets/images/avatar/13.jpg",
      "name": "Michael C. Phelan"
    },
    {
      "image": "assets/images/avatar/14.jpg",
      "name": "Lorraine S. Jones"
    },
    {
      "image": "assets/images/avatar/15.jpg",
      "name": "Philip J. Watson"
    },
    {
      "image": "assets/images/avatar/16.jpg",
      "name": "Patricia R. James"
    },
    {
      "image": "assets/images/avatar/17.jpg",
      "name": "Dena C. Fernandez"
    },
    {
      "image": "assets/images/avatar/18.jpg",
      "name": "Troy S. Gaines"
    },
    {
      "image": "assets/images/avatar/19.jpg",
      "name": "Robin K. Miller"
    },
    {
      "image": "assets/images/avatar/20.jpg",
      "name": "Willie K. Rothermel"
    },
  ];
  dayList: any[] = [
    {
      "name": "Sunday",
      "time": "10:00 AM - 10:00 PM"
    },
    {
      "name": "Monday",
      "time": "10:00 AM - 10:00 PM"
    },
    {
      "name": "Tuesday",
      "time": "10:00 AM - 10:00 PM"
    },
    {
      "name": "Wednesday",
      "time": "10:00 AM - 10:00 PM"
    },
    {
      "name": "Thursday",
      "time": "10:00 AM - 10:00 PM"
    },
    {
      "name": "Friday",
      "time": "10:00 AM - 10:00 PM"
    },
    {
      "name": "Saturday",
      "time": "10:00 AM - 10:00 PM"
    },
  ];
  chatList: any[] = [
    {
      "from": "a",
      "message": "Hello there. Thanks for the follow. Did you notice, that I am an egg? A talking egg? Damn!😄😄"
    },
    {
      "from": "b",
      "message": "	😃	😃	😃Yeah that is crazy, but people can change their own picture and build their own Twitter conversation with this generator, so it does not matter that you are an egg",
    },
    {
      "from": "a",
      "message": "Thanks mate! Feel way better now. Oh, and guys, these messages will be removed once your add your own :-)"
    },
    {
      "from": "b",
      "message": "You can then edit a message by clicking on it. This way you can change the text, status (check marks) and time. You can also determine whether the message was sent by the sender (right) or receiver (left)."
    },
    {
      "from": "a",
      "message": "😀😀You can change the order of messages by dragging and dropping them."
    },
    {
      "from": "b",
      "message": "Finally, click  (top right) to download your fake chat as an image."
    },
    {
      "from": "a",
      "message": "😀😀Thanks mate! Feel way better now. Oh, and guys, these messages will be removed once your add your own :-)"
    },
    {
      "from": "b",
      "message": "You also have the facility to hide the header and footer if needed."
    },
    {
      "from": "a",
      "message": "😀😀😀Customize the clock time and battery percentage as per your satisfaction."
    },
    {
      "from": "b",
      "message": "Now, make all the required changes for Person 2 also."
    },
    {
      "from": "a",
      "message": "If satisfied, download the chat and share with all your close friends and relatives, and note their reactions."
    },
    {
      "from": "b",
      "message": "😀😀Privacy comes first. Our tool does not store any data or chats by keeping in mind the privacy of our users"
    },
    {
      "from": "a",
      "message": "😀😀😀😀Our android text generator tool has an easy-to-use interface for the ease of the users. Also, the results generated by our tool are fast and realistic"
    },
    {
      "from": "b",
      "message": "Message privately. End-to-end encryption and privacy controls. Stay connected. Message and call for free* around the world. Build community. Group conversations made simple. Express yourself. Say it with stickers, voice, GIFs and more. WhatsApp business. Reach your customers from anywhere."
    },
    {
      "from": "a",
      "message": "Send a single message to multiple people at once"
    },
    {
      "from": "b",
      "message": "You can now send messages in bold, italics or strikethrough too. Simply use the special characters before and after the words to get the formatting of your choice"
    },
    {
      "from": "a",
      "message": "If you want to know who you are chatting too much with on WhatsApp, you can find out by simply scrolling through the chat screen"
    }
  ];
  constructor(
    public loadingCtrl: LoadingController,
    public alertCtrl: AlertController,
    private toastCtrl: ToastController,
    private navCtrl: NavController,
    private menuCtrl: MenuController,
    private router: Router,
    private zone: NgZone,
  ) { }

  navigateToPage(routes: any, param?: NavigationExtras | undefined) {
    this.zone.run(() => {
      console.log(routes, param);
      this.router.navigate([routes], param);
    });
  }

  navigateToProduct(id: number, name: string) {
    this.zone.run(() => {
      this.router.navigate(['product-details', id, name]);
    });
  }

  navigateRoot(routes: any | string) {
    this.zone.run(() => {
      this.navCtrl.navigateRoot([routes]);
    });
  }

  getKeys(key: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      resolve(localStorage.getItem(key))
    });
  }

  clearKeys(key: string) {
    // this.storage.remove(key);
    localStorage.removeItem(key);
  }

  setKeys(key: string, value: string): Promise<any> {
    return new Promise<any>((resolve, reject) => {
      resolve(localStorage.setItem(key, value));
    });
  }

  async show(msg?: string | null) {
    this.isLoading = true;
    return await this.loadingCtrl.create({
      cssClass: 'custom-loader',
      spinner: null,
      // message: msg && msg != '' && msg != null ? msg : '',
      // spinner: 'bubbles',
    }).then(a => {
      a.present().then(() => {
        //console.log('presented');
        if (!this.isLoading) {
          a.dismiss().then(() => console.log('abort presenting'));
        }
      });
    });
  }

  async hide() {
    this.isLoading = false;
    return await this.loadingCtrl.dismiss().then(() => console.log('dismissed'));
  }

  /*
    Show Warning Alert Message
    param : msg = message to display
    Call this method to show Warning Alert,
    */
  async showWarningAlert(msg: any) {
    const alert = await this.alertCtrl.create({
      header: 'Warning',
      message: msg,
      buttons: ['OK']
    });

    await alert.present();
  }

  async showSimpleAlert(msg: any) {
    const alert = await this.alertCtrl.create({
      header: '',
      message: msg,
      buttons: ['OK']
    });

    await alert.present();
  }

  /*
   Show Error Alert Message
   param : msg = message to display
   Call this method to show Error Alert,
   */
  async showErrorAlert(msg: any) {
    const alert = await this.alertCtrl.create({
      header: 'Error',
      message: msg,
      buttons: ['OK']
    });

    await alert.present();
  }

  /*
     param : email = email to verify
     Call this method to get verify email
     */
  async getEmailFilter(email: string) {
    const emailfilter = /^[\w._-]+[+]?[\w._-]+@[\w.-]+\.[a-zA-Z]{2,6}$/;
    if (!(emailfilter.test(email))) {
      const alert = await this.alertCtrl.create({
        header: 'Warning',
        message: 'Please enter valid email',
        buttons: ['OK']
      });
      await alert.present();
      return false;
    } else {
      return true;
    }
  }


  /*
    Show Toast Message on Screen
     param : msg = message to display, color= background
     color of toast example dark,danger,light. position  = position of message example top,bottom
     Call this method to show toast message
     */

  async showToast(msg: any, colors: any, positon: any) {


    const toast = await this.toastCtrl.create({
      message: msg,
      duration: 2000,
      color: colors,
      position: positon
    });
    toast.present();
    await Haptics.impact({ style: ImpactStyle.Medium });
  }
  async shoNotification(msg: any, colors: any, positon: any) {

    const toast = await this.toastCtrl.create({
      message: msg,
      duration: 4000,
      color: colors,
      position: positon,
      buttons: [
        {
          text: 'OK',
          role: 'cancel',
          handler: () => {
            // console.log('Cancel clicked');
          }
        }
      ]
    });
    toast.present();
    await Haptics.impact({ style: ImpactStyle.Medium });

  }

  async errorToast(msg: any, color?: string | (string & Record<never, never>) | undefined) {

    const toast = await this.toastCtrl.create({
      message: msg,
      duration: 2000,
      color: color ? color : 'dark'
    });
    toast.present();
    await Haptics.impact({ style: ImpactStyle.Medium });

  }

  onBack() {
    this.navCtrl.back();
  }

  makeid(length: any) {
    var result = '';
    var characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
    var charactersLength = characters.length;
    for (var i = 0; i < length; i++) {
      result += characters.charAt(Math.floor(Math.random() * charactersLength));
    }
    return result;
  }
}
