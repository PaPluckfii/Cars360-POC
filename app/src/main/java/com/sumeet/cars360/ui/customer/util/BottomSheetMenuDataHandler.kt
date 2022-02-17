package com.sumeet.cars360.ui.customer.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import androidx.core.content.ContextCompat
import androidx.core.widget.NestedScrollView
import com.bumptech.glide.Glide
import com.sumeet.cars360.R
import com.sumeet.cars360.databinding.CustomerServicesBottomSheetLayoutBinding
import com.sumeet.cars360.databinding.ItemBulletTextLayoutBinding
import com.sumeet.cars360.util.ViewVisibilityUtil

class BottomSheetMenuDataHandler {

    enum class MenuType {
        MECHANICAL_SERVICES,
        CUSTOM_SERVICES,
        CAR_DETAILING,
        LIFE_LONG_CARE,
        TIRES,
        CAR_WRAPPING,
        CAR_MODIFICATION,
        ACCESSORIES
    }

    lateinit var binding: CustomerServicesBottomSheetLayoutBinding

    fun resetData() {
        binding.apply {
            btnRequestService.text = "Schedule Appointment"
            nestedScrollView.scrollTo(NestedScrollView.FOCUS_UP, 0)
            nestedScrollView.visibility = View.GONE
            periodicServiceLayout.root.visibility = View.GONE
            customServiceLayout.root.visibility = View.GONE
            llPointersContent.removeAllViews()
        }
    }

    @SuppressLint("SetTextI18n")
    fun populateData(context: Context, menuType: MenuType) {
        when (menuType) {
            MenuType.MECHANICAL_SERVICES -> {
                binding.tvTitle.text = "Periodical Maintenance Service"

                Glide.with(binding.root).load(R.drawable.image_mechanical_repair_menu)
                    .into(binding.ivServiceImage)

                ViewVisibilityUtil.visible(binding.periodicServiceLayout.root)
                binding.periodicServiceLayout.apply {
                    val savingText1 = SpannableString(" You will be saving ")
                    savingText1.setSpan(ForegroundColorSpan(Color.BLACK),0,savingText1.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    val savingAmount = SpannableString("\u20B9 3000 ")
                    savingAmount.setSpan(ForegroundColorSpan(
                        ContextCompat.getColor(context,R.color.bright_green)
                    ),0,savingAmount.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    val savingText2 = SpannableString("with ")
                    savingText2.setSpan(ForegroundColorSpan(Color.BLACK),0,savingText2.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    val savingCars = SpannableString("Cars 360\u00B0 ")
                    savingCars.setSpan(ForegroundColorSpan(Color.RED),0,savingCars.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

                    tvCost.apply {
                        text = "\u20B9 3000"
                        paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    tvOurCost.text = "  \u20B9 3000"
                    tvSaving.setText(savingText1)
                    tvSaving.append(savingAmount)
                    tvSaving.append(savingText2)
                    tvSaving.append(savingCars)
                }

            }

            MenuType.CUSTOM_SERVICES -> {
                binding.tvTitle.text = "Custom Services"
                ViewVisibilityUtil.visible(binding.customServiceLayout.root)
            }

            MenuType.CAR_DETAILING -> {
                ViewVisibilityUtil.visible(binding.nestedScrollView)
                binding.tvTitle.text = "Car Detailing"

                val bullet1 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet1.apply {
                    tvHeading.text = "* What is Car Detailing?"
                    tvContent.text =
                        "Simply put, getting your car detailed means a top-to-bottom thorough" +
                                " cleaning of your vehicle using specialized tools and products. Normally, " +
                                "a detailer will also perform some light cosmetic touch-ups, but the process" +
                                " does not include paintwork or body repairs. Car detailing involves cleaning " +
                                "and reconditioning the interior and exterior of the car. The aim of this is to" +
                                " restore the paintwork by eliminating scratches or swirl marks to make the car " +
                                "look almost brand new like it did when you first drove it out of the shop."
                }

                val bullet2 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet2.apply {
                    tvHeading.text =
                        "* What is the difference between a Car Wash and an Auto Detail?"
                    tvContent.text =
                        "Now that you know what car detailing is, we are going to quickly outline" +
                                " the differences between a car wash and auto detailing, before going " +
                                "on to talk some more about why car detailing is a seriously good idea. " +
                                "Here at Car 360 , we get asked a lot of questions. There is " +
                                "one question that keeps popping up, however:"
                }

                val bullet3 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet3.apply {
                    tvHeading.text = "What is the difference between car detailing and car washing?"
                    tvContent.text =
                        "We realize that for most non-car enthusiasts, the difference probably isn't very " +
                                "significant. However, all car owners, enthusiasts or not, should look at" +
                                " getting their car fully detailed every so often, and should also perform " +
                                "smaller details regularly to have their car looking at its best all the time. " +
                                "So, to help our customers understand the many benefits of car detailing, also" +
                                " known as auto-detailing, we are going to outline what you can expect when you" +
                                " choose to have your car detailed. Trust us when we say that you'll never go" +
                                " back to normal washing!"
                }

                val bullet4 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet4.apply {
                    tvHeading.text = "* What are the steps in Auto Detailing?"
                    tvContent.text =
                        "Athorough car detailing consists of two main phases: interior detailing and " +
                                "exterior detailing. We are going to break down each phase so you know " +
                                "exactly what to expect when you get your vehicle detailed. Feel free to " +
                                "ask your technician if you have any questions about the process, as most" +
                                " of them love to share their art of detailing with others."
                }

                val bullet5 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet5.apply {
                    tvHeading.text = "1. Exterior Car Detailing:"
                    tvContent.text =
                        "We begin with an exhaustive exterior wash to remove as much dirt as possible. " +
                                "By allowing the soap to foam, dirt and mud are softened and lifted off the surface.\n" +
                                "Next, using special brushes and wheel cleaning products, the detailer takes off" +
                                " all the dust and filth from the wheels; brake calipers, lug nuts, etc. " +
                                "The wheels are usually the dirtiest parts of the car!\n" +
                                "After the wheels have been cleaned, the paintwork is washed and dried" +
                                " from top to bottom using washing mitts and soft microfiber towels." +
                                " The paintwork is then clayed using automotive clay barthat removes " +
                                "the tightly bonded dirt of the surface of the car, which is your car's " +
                                "clear coat. If required, the paint is polished to eliminate any light " +
                                "scratches, oxidation, and swirl marks. The paint can be polished by hand" +
                                " or by a polishing machine. This can be the most time-consuming part of " +
                                "the detailing service depending on the car and the state of its paintwork.\n" +
                                "Finally, the paint is waxed to give it an additional protective layer " +
                                "using high-quality carnauba based paste wax. This added protection results" +
                                " in a noticeable shine too, the kind that you saw when you first bought it!\n" +
                                "The remaining exterior parts, like the windows and other rubber parts, are" +
                                " then polished and methodically cleaned, adding the finishing touches in all" +
                                " the corners of the car's body and exterior trim to make the entire car sparkle."
                }

                val bullet6 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet6.apply {
                    tvHeading.text = "2. Interior Car Detailing"
                    tvContent.text = "All of the upholstery inside the car is thoroughly" +
                            " vacuumed and shampooed to remove stains and dirt. If the car " +
                            "has leatherwork, this is often conditioned and scrubbed to remove " +
                            "dirt that is deeply ingrained. Plastics and vinyl are also properly " +
                            "cleaned and dressed.\n" +
                            "Lastly, the interior glass is also cleaned and polished."
                }

                Glide.with(binding.root).load(R.drawable.image_car_detailing_menu)
                    .into(binding.ivServiceImage)

                binding.llPointersContent.apply {
                    addView(bullet1.root)
                    addView(bullet2.root)
                    addView(bullet3.root)
                    addView(bullet4.root)
                    addView(bullet5.root)
                    addView(bullet6.root)
                }
            }
            MenuType.LIFE_LONG_CARE -> {
                binding.tvTitle.text = "Life Long Car Care"

                val descIntro = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                descIntro.apply {
                    ViewVisibilityUtil.gone(tvHeading)
                    tvHeading.text =
                        "No Data Right Now"
                }

                Glide.with(binding.root).load(R.drawable.image_life_long_care_menu)
                    .into(binding.ivServiceImage)

                binding.llPointersContent.apply {
                    addView(descIntro.root)
                }
            }
            MenuType.TIRES -> {
                binding.tvTitle.text = "Tires"
                ViewVisibilityUtil.visible(binding.nestedScrollView)
                val descIntro = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                descIntro.apply {
                    ViewVisibilityUtil.gone(tvHeading)
                    tvHeading.text = "CARS 360 HELPS YOU GETTING THE PERFECT TYRES FOR YOUR CAR"
                    tvContent.text =
                        "What do you think the most important part of your car is? The steering wheel," +
                                " the breaks, the seatbelt? Well, you'd be wrong as the most important" +
                                " part of any car is definitely the tyres."
                }

                val bullet1 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet1.apply {
                    tvHeading.text = "WHY ARE TYRES IMPORTANT?"
                    tvContent.text =
                        "Think about it, tyres are the only thing in your vehicle that actually touch the floor. This means that they are in control of your steering, breaking, acceleration and absorbing all the bumps that the road may throw at you.\n" +
                                "When you look at it this way, it becomes obvious that having good quality tyres is essential" +
                                " to your driving experience. There are many different types of tyres available on the market " +
                                "and not all of them provide the same levels of performance. Certain tyres provide much better " +
                                "traction in wet weather or snow, while others provide smoother rolling at high speeds for better" +
                                " fuel consumption and reduced noise."
                }

                val bullet2 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet2.apply {
                    tvHeading.text = "FINDING THE RIGHT TYRES"
                    tvContent.text =
                        "It is important that you pick tyres that are well suited for the" +
                                " conditions that you most regularly drive in. Many" +
                                " people tend to choose budget tyres for the cost saving" +
                                " factor, however this can be false economy, as cheap tyres " +
                                "tend to go bald quicker, can increase fuel consumption and " +
                                "may not grip the road as well. " +
                                "Not all tyres are created equal, so it pays to make sure that" +
                                " the tyres you are looking to buy match your requirements. If " +
                                "you do a lot of motorway driving then you will want tyres that" +
                                " handle much better at higher speeds, while countryside drivers " +
                                "may look for tyres that can easily handle a range of terrain. You" +
                                " are also able to purchase tyres made from a tougher compound making " +
                                "them much longer lasting and with better grip than budget tyres."
                }

                val bullet3 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet3.apply {
                    tvHeading.text = "MAINTAINING YOUR TYRES"
                    tvContent.text =
                        "Regardless of what tyres you end up buying, there are several checks that will need to be" +
                                " regularly carried out to prevent damage or excessive wear. At least once a " +
                                "month you should carry out checks including:\n" +
                                "Tyre pressure • Bulges  or  cracks  in  the  tyre\n" +
                                "Tread depth – the legal minimum is 1.6mm • Uneven wear\n" +
                                "You can adjust the tyre pressure yourself at home or any petrol " +
                                "station, but if you notice any of these other issues then you will " +
                                "need to replace your tyres straight away.\n" +
                                "Nitrogen Inflator\n" +
                                "If you are one of the 85% of Indians who don't regularly check tire" +
                                " pressure, you need nitrogen.\n" +
                                "We take in nitrogen with every breath. Air is composed of:\n" +
                                "1% Water Vapor and Other Gases – Escapes up to 250 times faster than Nitrogen\n" +
                                "21% Oxygen – Escapes 3-4 times faster than Nitrogen\n" +
                                "78% Nitrogen – The largest molecule in air, dry, non-flammable.\n" +
                                "Because of their large size, nitrogen molecules are the least permeable and stay " +
                                "in your tire longer.\n" +
                                "It's not about the nitrogen. It's about reducing oxygen, water vapor and other gases.\n" +
                                "By reducing the percentage of oxygen, water vapor and other gases in your tires " +
                                "from 22% to 7% or lower, your tires will maintain proper pressure longer than if" +
                                " you use “plain old air.” For example, with 95% nitrogen in your tires, they" +
                                " retain optimal pressure three to four times longer.\n" +
                                "Proper tire pressure is a big deal. Maintain it with nitrogen, and you'll see" +
                                " these three primary benefits\n" +
                                "Increased Fuel Efficiency – Correct tire pressure keeps the manufacturer's " +
                                "recommended “contact patch” on the road. This lessens the rolling resistance" +
                                " and maximizes fuel efficiency.\n" +
                                "Longer Tire Life – When it comes in contact with other materials, oxygen " +
                                "causes oxidation. Oxidation can make rubber brittle and cause it to lose tensile " +
                                "strength. In addition, at high temperatures and pressures, oxygen reacts and" +
                                " damages inner tire liners and belt packages; nitrogen does not.\n" +
                                "Increased Safety – Under-inflated tires cause 90% of blowouts. Nitrogen provides " +
                                "more reliable pressure for reduced blowout potential."
                }

                val bullet4 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet4.apply {
                    tvHeading.text = "OTHER BENEFITS:"
                    tvContent.text =
                        "Improved TPMS Performance – If you have a new car, you likely are plagued by " +
                                "a flashing light telling you your tire pressure is low." +
                                " For example, one woman's light was going off every four to " +
                                "five weeks. After inflating with nitrogen, her light didn't reappear for 53 weeks!\n" +
                                "More Predictable Pressure Fluctuation – NASCAR teams use nitrogen" +
                                " so they can more accurately predict tire pressure fluctuation." +
                                " Regular compressed air can fluctuate considerably when water vapor is present.\n" +
                                "Longer Rim Life – Rim rust caused by condensation from water vapor " +
                                "and other gases can get caught in valves and create slow leaks in" +
                                " tires. Nitrogen is completely dry, so it eliminates the potential for condensation."
                }

                Glide.with(binding.root).load(R.drawable.image_tyres_menu)
                    .into(binding.ivServiceImage)

                binding.llPointersContent.apply {
                    addView(descIntro.root)
                    addView(bullet1.root)
                    addView(bullet2.root)
                    addView(bullet3.root)
                    addView(bullet4.root)
                }
            }
            MenuType.CAR_WRAPPING -> {
                binding.tvTitle.text = "Car Wrapping"
                ViewVisibilityUtil.visible(binding.nestedScrollView)
                val descIntro = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                descIntro.apply {
                    ViewVisibilityUtil.gone(tvHeading)
                    tvHeading.text = "What is a car wrap anyway?"
                    tvContent.text =
                        "A wrap is a large vinyl graphic or decal. It is applied directly" +
                                " over the original paint of the vehicle. The application" +
                                " of the wrap allows you to change the vehicle’s appearance" +
                                " in a very short period of time and in turn allows you to" +
                                " remove the wrap, returning the vehicle back to its original" +
                                " condition if necessary."
                }

                val bullet1 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet1.apply {
                    tvHeading.text = "What’s involved in a car wrap?"
                    tvContent.text = "Car and vehicle wraps, including bus wraps and fleet wraps," +
                            " are comprised of three phases. The first is the design phase which" +
                            " includes getting accurate measurements for the vehicle and actual design" +
                            " of the graphics to be applied. The second is the production phase where " +
                            "the graphic is printed and then laminated to protect the vinyl from abrasions " +
                            "and UV rays that can cause graphics to fade over time. The third phase is " +
                            "installation where the vinyl is actually applied to your vehicle. In some cases" +
                            " there may even be a fourth phase, the removal of graphics if requested."
                }

                val bullet2 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet2.apply {
                    tvHeading.text = "* INCREASED VEHICLE PERFORMANCE & RELIABILITY"
                    tvContent.text =
                        "If you are uncompromising with the high level of service you provide " +
                                "your vehicle, you can be sure that your vehicle will return the favour with " +
                                "being more dependable to get you where you need to go. If you pay a little more " +
                                "attention to a vehicles vital fluids, oils, and various parts, you can be sure to " +
                                "reduce the internal wear and tear."
                }

                val bullet3 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet3.apply {
                    tvHeading.text =
                        "What’s the difference between a partial and full vehicle wrap?"
                    tvContent.text =
                        "Anything less than a full wrap is normally called a “partial wrap”. A half " +
                                "partial wrap normally includes the entire rear of the vehicle " +
                                "and halfway up the vehicle, and includes a hood logo. A three-quarters " +
                                "partial wrap normally includes the entire rear of the vehicle and most " +
                                "of the way up the vehicle, and includes a hood logo. Typically, a full wrap" +
                                " includes the entire surface of the vehicle. No roof, roof wrapping is additional."
                }

                val bullet4 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet4.apply {
                    tvHeading.text = "How long will the wrap stay on my vehicle?"
                    tvContent.text =
                        "Normally, you can leave a wrap on between one to five years."
                }

                val bullet5 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet5.apply {
                    tvHeading.text = "Will a vehicle wrap damage my paint?"
                    tvContent.text =
                        "No. In most cases vehicle wraps will not damage factory paint jobs. " +
                                "Wraps do not stick to rust. If your vehicle has chipping paint" +
                                " a wrap may pull the chipping paint when removed."
                }

                val bullet6 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet6.apply {
                    tvHeading.text = "Do I have to wash my vehicle before installation?"
                    tvContent.text = "Yes. All vehicles have to be free of dust, mud, wax, oil," +
                            " armor-all type products, and other agents that may prevent the vinyl " +
                            "from adhering to the vehicle surface during the installation process."
                }

                Glide.with(binding.root).load(R.drawable.image_car_wrapping_menu)
                    .into(binding.ivServiceImage)

                binding.llPointersContent.apply {
                    addView(descIntro.root)
                    addView(bullet1.root)
                    addView(bullet2.root)
                    addView(bullet3.root)
                    addView(bullet4.root)
                    addView(bullet5.root)
                    addView(bullet6.root)
                }
            }
            MenuType.CAR_MODIFICATION -> {
                binding.tvTitle.text = "Car Modification"
                ViewVisibilityUtil.visible(binding.nestedScrollView)
                val descIntro = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                descIntro.apply {
                    ViewVisibilityUtil.gone(tvHeading)
                    tvContent.text =
                        "Give your car a makeover and a new look with our car vinyl wrap ﬁlms. Give " +
                                "your car a premium look and enhance its aesthetic appeal with the" +
                                " Wrap Film. It is a removable, cast ﬁlm for vehicle detailing, " +
                                "decoration and full car wraps. The ﬁlms are engineered to conform to the" +
                                " curves & contours of an automobile. When required, it can be heated to" +
                                " curve and conform around the most complex car surfaces, like a rear view" +
                                " mirror for example. The Superior dual cast vinyl car wrap is available in" +
                                " Carbon Fiber, Matte & Gloss ﬁnishes. Range of 76 wrap ﬁlms bring users an " +
                                "even greater number of out-of-the-ordinary ﬁnishes, textures and color options" +
                                " to create customized vehicle wraps that stand out from the crowd. These removable" +
                                " adhesive wrap ﬁlms are easy to remove, and leave minimal adhesive residue on removal" +
                                " without damaging the paint of your car.\n" +
                                "\n" +
                                "The digitally printed Car Wraps are not just for the fashionable." +
                                " It's becoming popular among car owners across the spectrum. Some choose" +
                                " a wrap for enhancing aesthetics and some for practical, functional uses." +
                                " It's a second skin for our 4wheeled friends."
                }

                Glide.with(binding.root).load(R.drawable.image_car_modification_menu)
                    .into(binding.ivServiceImage)

                binding.llPointersContent.apply {
                    addView(descIntro.root)
                }
            }
            MenuType.ACCESSORIES -> {
                binding.tvTitle.text = "Accessories"
                ViewVisibilityUtil.visible(binding.nestedScrollView)
                val descIntro = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                descIntro.apply {
                    ViewVisibilityUtil.gone(tvHeading)
                    tvContent.text =
                        "Get Branded Accessories for your Car From Cars360"
                }

                val bullet1 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet1.apply {
                    tvHeading.text = "* Car Cover"
                    tvContent.text =
                        "Protect that ride, when your not riding in it. Every car needs a " +
                                "cover especially for those cars that cannot be parked under a roofed garage. " +
                                "It will protect your car from the elements as well as the occasional bird droppings." +
                                " If you don't drive that ride everyday, put it under a cover." +
                                " That bit of bird poo will destroy your paint in less time that it takes " +
                                "you to notice its there. Covers come relatively cheap and in all shapes and sizes. "
                }

                val bullet2 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet2.apply {
                    tvHeading.text = "* Floor Mats"
                    tvContent.text =
                        "Your car floor takes a good beating from your footwear. To protect your car floor" +
                                " from the dust and debris that your footwear carries into your car, get a good " +
                                "set of car floor mats. They are available for most makes and models in the market." +
                                " They also add the benefit of customising your car interior with the available colours" +
                                " and materials for your custom floor mats. Some even have designs and trims to choose" +
                                " from. You can choose your car model to see our available floor mats."
                }

                val bullet3 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet3.apply {
                    tvHeading.text = "* Dashboard Mats"
                    tvContent.text =
                        "Keep your dash looking brand new with dash mats. Dash mats protect your" +
                                " dash from the sun and also from spills from food and beverages." +
                                " It will help your dashboard last longer and also adds another level " +
                                "of customization. They also come in a host of materials and colours to" +
                                " accent or compliment your car interior. Dash mats also help eliminate" +
                                " the glare from your dashboard when the sun is shining brightly at mid-day."
                }

                val bullet4 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet4.apply {
                    tvHeading.text = "* Seat Covers"
                    tvContent.text =
                        "Protect your car seats with seat covers. Those factory seats won't last very " +
                                "long against stains and debris, especially when you have kids who love " +
                                "to order drive-thru meals and consume them inside your car. Protect your " +
                                "car seats with seat covers that are detachable and can be cleaned. You have " +
                                "a choice of materials from soft suede to luxurious leather. They can come " +
                                "fully customised too with a host of colours and trims."
                }

                val bullet5 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet5.apply {
                    tvHeading.text = "* Car Charger"
                    tvContent.text =
                        "We have a lot of gadgets that need charging. A good car charger will help you power" +
                                " gadgets especially on those long journeys. Look for those that have docks " +
                                "so that your phone is safe when you hit potholes or speed bumps. You can also" +
                                " opt for those universal chargers that can accommodate any mobile gadget that " +
                                "needs charging. "
                }

                val bullet6 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet6.apply {
                    tvHeading.text = "* Jumper Kit"
                    tvContent.text =
                        "You don't want to be stuck in the middle of nowhere waiting for" +
                                " someone to give your car a much-needed jumpstart. There are a lot of jumper " +
                                "kits around that are portable, lightweight and easy to use. A good set would " +
                                "include all the necessary cables and a good battery. This will save you a lot of" +
                                " time and cash because those roadside assistance companies charge huge sums just " +
                                "for a simple jump start"
                }

                val bullet7 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet7.apply {
                    tvHeading.text = "* Cup Holders"
                    tvContent.text =
                        "Don't spill your coffee in the car! Love your Latte in the morning or a big soda? " +
                                "Better get a good set of cup holders for your car. The OEM cup holders do not fit all" +
                                " cup sizes so better get a set that can adjust to the cup size. This also helps protect " +
                                "your interior from spills and splashes. You don't want to be cleaning that suede floor " +
                                "after you spilt your coffee on it now do you?"
                }

                val bullet8 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet8.apply {
                    tvHeading.text = "* GPS"
                    tvContent.text =
                        "Get directions to your destination. Never get lost again by having a trusty " +
                                "GPS system with you at all times. Whether it's for looking up hard to find places" +
                                " or mapping the best route to your destination, the GPS will help you with it. " +
                                "There are a number of selections from mobile phone based GPS or your built-in SATNAV " +
                                "system. Just be sure you have one on your car."
                }

                val bullet9 = ItemBulletTextLayoutBinding.inflate(
                    LayoutInflater.from(context),
                    binding.llPointersContent,
                    false
                )
                bullet9.apply {
                    tvHeading.text = "* Toolbox"
                    tvContent.text =
                        "Always be prepared, especially on long journeys. Make sure you bring a" +
                                " good toolbox with you inside your car. A toolbox should have a Jack, a set of" +
                                " wrenches, screwdrivers, cables, and all the other things you need to do a little" +
                                " DIY fixing. You can add a tow cable into the mix if you need a tow or help someone out. "
                }

                Glide.with(binding.root).load(R.drawable.image_accessories_menu)
                    .into(binding.ivServiceImage)

                binding.llPointersContent.apply {
                    addView(descIntro.root)
                    addView(bullet1.root)
                    addView(bullet2.root)
                    addView(bullet3.root)
                    addView(bullet4.root)
                    addView(bullet5.root)
                    addView(bullet6.root)
                    addView(bullet7.root)
                    addView(bullet8.root)
                    addView(bullet9.root)
                }
            }
        }
    }

}